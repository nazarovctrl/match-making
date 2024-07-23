package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.*;
import uz.ccrew.matchmaking.repository.*;
import uz.ccrew.matchmaking.util.AuthUtil;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.util.PlayerUtil;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;
import uz.ccrew.matchmaking.dto.match.TeamDTO;
import uz.ccrew.matchmaking.enums.LobbyStatus;
import uz.ccrew.matchmaking.dto.match.MatchDTO;
import uz.ccrew.matchmaking.service.EloService;
import uz.ccrew.matchmaking.service.MatchService;
import uz.ccrew.matchmaking.util.LobbyPlayerUtil;
import uz.ccrew.matchmaking.mapper.TeamPlayerMapper;
import uz.ccrew.matchmaking.exp.BadRequestException;
import uz.ccrew.matchmaking.dto.match.MatchResultDTO;
import uz.ccrew.matchmaking.exp.ServerUnavailableException;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final AuthUtil authUtil;
    private final PlayerUtil playerUtil;
    private final EloService eloService;
    private final TeamRepository teamRepository;
    private final LobbyRepository lobbyRepository;
    private final LobbyPlayerUtil lobbyPlayerUtil;
    private final MatchRepository matchRepository;
    private final ServerRepository serverRepository;
    private final TeamPlayerMapper teamPlayerMapper;
    private final MatchAsyncService matchAsyncService;
    private final TeamPlayerRepository teamPlayerRepository;
    private final LobbyPlayerRepository lobbyPlayerRepository;


    @Override
    public void join() { //TODO send less query to database
        LobbyPlayer lobbyPlayer = lobbyPlayerUtil.loadLobbyPlayer();
        lobbyPlayerUtil.checkToLeader(lobbyPlayer);

        Lobby lobby = lobbyPlayer.getLobby();
        if (lobby.getStatus().equals(LobbyStatus.WAITING)) {
            throw new BadRequestException("You are already in queue");
        } else if (lobby.getStatus().equals(LobbyStatus.IN_GAME)) {
            throw new BadRequestException("You are already in game");
        }

        Player player = lobbyPlayer.getPlayer();
        MatchMode mode = lobby.getMatchMode();
        TeamType teamType = lobby.getTeamType();

        Optional<Match> matchOptional;
        Optional<Team> teamOptional;

        List<LobbyPlayer> lobbyPlayers = lobbyPlayerRepository.findByLobby_Id(lobby.getId());

        if (lobbyPlayers.size() == teamType.getPlayerCount()) {
            matchOptional = matchRepository.findMatch(player.getRank(), mode, teamType, mode.getTeamCount());
            teamOptional = Optional.empty();
        } else {
            teamOptional = teamRepository.findTeam(player.getRank(), mode, teamType, lobbyPlayers.size(), teamType.getPlayerCount());
            matchOptional = teamOptional.map(Team::getMatch)
                    .or(() -> matchRepository.findMatch(player.getRank(), mode, teamType, mode.getTeamCount()));
        }

        Match match;
        if (matchOptional.isPresent()) {
            match = matchOptional.get();
        } else {
            match = new Match(mode, teamType, player.getRank(), getFreeServer());
            matchRepository.save(match);
        }

        Team team = teamOptional.orElseGet(() -> teamRepository.save(new Team(match)));
        List<TeamPlayer> teamPlayerList = lobbyPlayers.stream().map(lp -> new TeamPlayer(team, lp.getPlayer())).toList();
        teamPlayerRepository.saveAll(teamPlayerList);

        lobby.setStatus(LobbyStatus.WAITING);
        lobbyRepository.save(lobby);

        matchAsyncService.checkMatchToFull(match, mode, teamType);
    }

    @Override
    public MatchDTO get(String matchId) {
        //TODO check user to exists in the match
        //TODO use caching
        Match match = matchRepository.loadById(UUID.fromString(matchId));
        List<Team> teams = teamRepository.findByMatch_MatchId(match.getMatchId());

        List<TeamDTO> teamDTOList = new ArrayList<>();
        for (Team team : teams) {
            List<TeamPlayer> teamPlayers = teamPlayerRepository.findByTeam(team);

            TeamDTO teamDTO = TeamDTO.builder()
                    .teamId(team.getTeamId().toString())
                    .number(team.getNumber())
                    .players(teamPlayerMapper.toDTOList(teamPlayers)).build();

            teamDTOList.add(teamDTO);
        }

        return MatchDTO.builder()
                .matchId(matchId)
                .mode(match.getMode())
                .teamType(match.getTeamType())
                .status(match.getStatus())
                .teams(teamDTOList).build();
    }

    @Override
    public void readyToPlay(boolean isReady) {
        Player player = playerUtil.loadPLayer();
        Optional<TeamPlayer> optional = matchRepository.findByPlayerIdAndMatchStatus(player.getPlayerId(), MatchStatus.WAITING);
        if (optional.isEmpty()) {
            throw new BadRequestException("You are not in match to confirm play or your match already canceled");
        }

        TeamPlayer teamPlayer = optional.get();
        if (teamPlayer.getIsReady() != null) {
            throw new BadRequestException("Already confirmed or declined");
        }

        teamPlayer.setIsReady(isReady);
        teamPlayerRepository.save(teamPlayer);

        Match match = teamPlayer.getTeam().getMatch();

        if (isReady) {
            matchAsyncService.checkMatchToStart(match);
        } else {
            matchAsyncService.cancelMatch(match);
        }
    }

    private Server getFreeServer() {
        Server server = serverRepository.findFirstByIsBusyIsFalseOrderByLastModifiedDate()
                .orElseThrow(() -> new ServerUnavailableException("There is no available server to play match"));
        server.setIsBusy(true);
        serverRepository.save(server);
        return server;
    }

    @Transactional
    @Override
    public void calculateResult(MatchResultDTO dto) {
        UUID matchUUID = UUID.fromString(dto.matchId());
        Match match = matchRepository.loadById(matchUUID);
        if (!match.getServer().getUser().getId().equals(authUtil.loadLoggedUser().getId())) {
            throw new BadRequestException("You cant calculate result for another server match");
        }

        if (!match.getStatus().equals(MatchStatus.STARTED)) {
            throw new BadRequestException("Match status is not Started");
        }
        Server server = match.getServer();
        server.setIsBusy(false);
        serverRepository.save(server);

        match.setStatus(MatchStatus.FINISHED);
        lobbyRepository.updateStatusByMatchId(matchUUID, LobbyStatus.PREPARING);
        matchRepository.save(match);
        List<Team> teams = new ArrayList<>();

        dto.teamResults().forEach(teamResult -> {
            Team team = teamRepository.loadById(UUID.fromString(teamResult.teamId()));
            team.setPlacement(teamResult.place());
            teams.add(team);
        });

        List<Player> winners = new ArrayList<>();
        List<Player> losers = new ArrayList<>();
        for (Team team : teams) {
            List<Player> players = teamPlayerRepository.findByTeamId(team.getTeamId());
            if (team.getPlacement() == 1) {
                winners.addAll(players);
            } else {
                losers.addAll(players);
            }
        }

        teamRepository.saveAll(teams);
        if (match.getMode().equals(MatchMode.TDM)) {
            if (match.getTeamType().equals(TeamType.SOLO)) {
                eloService.updateRating(winners.getFirst(), losers.getFirst());
            } else {
                eloService.updateTeamRatings(winners, losers);
            }
        } else {
            if (match.getTeamType().equals(TeamType.SOLO)) {
                eloService.updateFFARatings(teams);
            } else {
                eloService.updateFFATeamRatings(teams);
            }
        }
    }
}
