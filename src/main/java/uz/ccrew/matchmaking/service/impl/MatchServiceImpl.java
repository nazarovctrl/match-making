package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.*;
import uz.ccrew.matchmaking.repository.*;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;
import uz.ccrew.matchmaking.dto.match.MatchDTO;
import uz.ccrew.matchmaking.mapper.MatchMapper;
import uz.ccrew.matchmaking.service.MatchService;
import uz.ccrew.matchmaking.util.LobbyPlayerUtil;
import uz.ccrew.matchmaking.exp.ServerUnavailableException;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    private void checkMatchToPrepare(Match match, MatchMode mode, TeamType teamType) {
        Integer teamCount = teamRepository.countByMatch_MatchId(match.getMatchId());
        if (teamCount == null || teamCount < mode.getTeamCount()) {
            return;
        }

        Boolean existsNotFullTeam = teamPlayerRepository.existNotFullTeam(match.getMatchId(), teamType.getPlayerCount());
        if (existsNotFullTeam != null && existsNotFullTeam) {
            return;
        }

        //TODO send notification to match players and change their lobby status t
        match.setStatus(MatchStatus.PREPARED);
        matchRepository.save(match);
    }
}
