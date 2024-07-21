package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.*;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;
import uz.ccrew.matchmaking.enums.LobbyStatus;
import uz.ccrew.matchmaking.repository.TeamRepository;
import uz.ccrew.matchmaking.websocket.WebSocketService;
import uz.ccrew.matchmaking.repository.LobbyRepository;
import uz.ccrew.matchmaking.repository.MatchRepository;
import uz.ccrew.matchmaking.repository.TeamPlayerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MatchAsyncService {
    private final TeamRepository teamRepository;
    private final LobbyRepository lobbyRepository;
    private final MatchRepository matchRepository;
    private final WebSocketService webSocketService;
    private final TeamPlayerRepository teamPlayerRepository;


    @Async
    public void checkMatchToStart(Match match, MatchMode mode, TeamType teamType) {
        if (!isMatchReady(match, mode, teamType)) {
            return;
        }

        lobbyRepository.updateStatusByMatchId(match.getMatchId(), LobbyStatus.IN_GAME);
        initializeNumbers(match.getMatchId());

        //sending notification
        List<String> users = teamPlayerRepository.findByMatchId(match.getMatchId());
        users.add(match.getServer().getUser().getLogin());
        webSocketService.sendMessage(users, String.format("Match started matchId=%s", match.getMatchId()));

        match.setStatus(MatchStatus.STARTED);
        matchRepository.save(match);
    }

    private boolean isMatchReady(Match match, MatchMode mode, TeamType teamType) {
        Integer teamCount = teamRepository.countByMatch_MatchId(match.getMatchId());
        if (teamCount == null || teamCount < mode.getTeamCount()) {
            return false;
        }

        Boolean existsNotFullTeam = teamPlayerRepository.existNotFullTeam(match.getMatchId(), teamType.getPlayerCount());
        return !Boolean.TRUE.equals(existsNotFullTeam);
    }

    public void initializeNumbers(UUID matchId) {
        List<Team> teams = teamRepository.findByMatch_MatchId(matchId);
        int teamNumber = 1;

        List<TeamPlayer> allTeamPlayers = new ArrayList<>();

        for (Team team : teams) {
            List<TeamPlayer> teamPlayers = teamPlayerRepository.findByTeam(team);
            allTeamPlayers.addAll(teamPlayers);

            int playerNumber = 1;
            for (TeamPlayer teamPlayer : teamPlayers) {
                teamPlayer.setNumber(playerNumber++);
            }
            team.setNumber(teamNumber++);
        }

        teamPlayerRepository.saveAll(allTeamPlayers);
        teamRepository.saveAll(teams);
    }
}
