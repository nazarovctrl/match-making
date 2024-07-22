package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.*;
import uz.ccrew.matchmaking.repository.*;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;
import uz.ccrew.matchmaking.enums.LobbyStatus;
import uz.ccrew.matchmaking.websocket.WebSocketService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MatchAsyncService {
    private final TeamRepository teamRepository;
    private final LobbyRepository lobbyRepository;
    private final MatchRepository matchRepository;
    private final WebSocketService webSocketService;
    private final TeamPlayerRepository teamPlayerRepository;

    @Async
    public void checkMatchToFull(Match match, MatchMode mode, TeamType teamType) {
        if (!isMatchFull(match, mode, teamType)) {
            return;
        }
        //sending notification
        List<String> users = teamPlayerRepository.findLoginsByMatchId(match.getMatchId());
        webSocketService.sendMessage(users, String.format("Confirm matchId=%s", match.getMatchId()));

        match.setStatus(MatchStatus.WAITING);
        matchRepository.save(match);
    }

    @Async
    public void checkMatchToStart(Match match) {
        Boolean isNotReady = matchRepository.isNotReadyToStart(match.getMatchId());
        if (isNotReady) {
            return;
        }

        initializeNumbers(match.getMatchId());
        lobbyRepository.updateStatusByMatchId(match.getMatchId(), LobbyStatus.IN_GAME);

        match.setStatus(MatchStatus.STARTED);
        matchRepository.save(match);

        //sending notification
        List<String> users = teamPlayerRepository.findLoginsByMatchId(match.getMatchId());
        users.add(match.getServer().getUser().getLogin());
        webSocketService.sendMessage(users, String.format("Match started matchId=%s", match.getMatchId()));
    }

    private boolean isMatchFull(Match match, MatchMode mode, TeamType teamType) {
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
