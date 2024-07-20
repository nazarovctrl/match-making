package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.*;
import uz.ccrew.matchmaking.repository.*;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;
import uz.ccrew.matchmaking.dto.match.TeamDTO;
import uz.ccrew.matchmaking.dto.match.MatchDTO;
import uz.ccrew.matchmaking.mapper.MatchMapper;
import uz.ccrew.matchmaking.service.EloService;
import uz.ccrew.matchmaking.service.MatchService;
import uz.ccrew.matchmaking.util.LobbyPlayerUtil;
import uz.ccrew.matchmaking.mapper.TeamPlayerMapper;
import uz.ccrew.matchmaking.exp.ServerUnavailableException;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchMapper matchMapper;
    private final TeamRepository teamRepository;
    private final LobbyPlayerUtil lobbyPlayerUtil;
    private final MatchRepository matchRepository;
    private final ServerRepository serverRepository;
    private final TeamPlayerRepository teamPlayerRepository;
    private final LobbyPlayerRepository lobbyPlayerRepository;
    private final TeamPlayerMapper teamPlayerMapper;
    private final EloService eloService;

    @Transactional
    @Override
    public MatchDTO find() {
        LobbyPlayer lobbyPlayer = lobbyPlayerUtil.loadLobbyPlayer();
        lobbyPlayerUtil.checkToLeader(lobbyPlayer);

        Player player = lobbyPlayer.getPlayer();
        Lobby lobby = lobbyPlayer.getLobby();
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
            Optional<Server> optional = serverRepository.findFirstByIsBusyIsFalseOrderByLastModifiedDate();
            if (optional.isEmpty()) {
                throw new ServerUnavailableException("There is no available server to play match");
            }
            match = new Match(mode, teamType, player.getRank(), optional.get());
            matchRepository.save(match);
        }

        Team team = teamOptional.orElseGet(() -> teamRepository.save(new Team(match)));
        List<TeamPlayer> teamPlayerList = lobbyPlayers.stream().map(lp -> new TeamPlayer(team, lp.getPlayer())).toList();
        teamPlayerRepository.saveAll(teamPlayerList);

        //checking to start match
        checkMatchToPrepare(match, mode, teamType);

        return matchMapper.toDTO(match);
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
            TeamDTO build = TeamDTO.builder()
                    .teamId(team.getTeamId().toString())
                    .number(team.getNumber())
                    .players(teamPlayerMapper.toDTOList(teamPlayers)).build();
            teamDTOList.add(build);
        }

        return MatchDTO.builder()
                .matchId(matchId)
                .mode(match.getMode())
                .teamType(match.getTeamType())
                .status(match.getStatus())
                .teams(teamDTOList).build();
    }

    private void checkMatchToPrepare(Match match, MatchMode mode, TeamType teamType) {
        Integer teamCount = teamRepository.countByMatch_MatchId(match.getMatchId());
        if (teamCount == null || teamCount < mode.getTeamCount()) {
            return;
        }

        Boolean existsNotFullTeam = teamPlayerRepository.existNotFullTeam(match.getMatchId(), teamType.getPlayerCount());
        if (existsNotFullTeam != null && existsNotFullTeam) {
            return;
        }

        //TODO send notification to match players and change their lobby status to WAITING
        match.setStatus(MatchStatus.PREPARED);
        matchRepository.save(match);
    }

    public void handleResultForSolo(String matchId,String teamID){
        UUID matchUUID = UUID.fromString(matchId);
        Match match = matchRepository.loadById(matchUUID);
        List<Team> teams = teamRepository.findByMatch_MatchId(matchUUID);
        List<Player> winners = new ArrayList<>();
        List<Player> losers = new ArrayList<>();
        for (Team team : teams) {
            List<Player> players = teamPlayerRepository.findByTeamId(team.getTeamId());
            if (team.getTeamId().toString().equals(teamID)) {
                winners.addAll(players);
            }else {
                losers.addAll(players);
            }
        }

        if (match.getTeamType().equals(TeamType.SQUAD)){
            eloService.updateRatings(winners,losers);
        }else {
            eloService.updateRatings(winners.getFirst(),losers.getFirst());
        }
    }
}
