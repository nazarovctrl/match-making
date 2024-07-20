package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.*;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;
import uz.ccrew.matchmaking.enums.LobbyStatus;
import uz.ccrew.matchmaking.repository.TeamRepository;
import uz.ccrew.matchmaking.repository.LobbyRepository;
import uz.ccrew.matchmaking.repository.MatchRepository;
import uz.ccrew.matchmaking.repository.TeamPlayerRepository;

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
    private final TeamPlayerRepository teamPlayerRepository;
    private final LobbyRepository lobbyRepository;
    private final MatchRepository matchRepository;

    @Async
    public void checkMatchToStart(Match match, MatchMode mode, TeamType teamType) {
        Integer teamCount = teamRepository.countByMatch_MatchId(match.getMatchId());
        if (teamCount == null || teamCount < mode.getTeamCount()) {
            return;
        }

        Boolean existsNotFullTeam = teamPlayerRepository.existNotFullTeam(match.getMatchId(), teamType.getPlayerCount());
        if (existsNotFullTeam != null && existsNotFullTeam) {
            return;
        }

        //TODO send notification to match players and change their lobby status to WAITING
        List<Lobby> lobbies = lobbyRepository.findByMatchId(match.getMatchId());
        lobbies.forEach(lobby -> lobby.setStatus(LobbyStatus.IN_GAME));
        lobbyRepository.saveAll(lobbies);

        initializeNumber(match.getMatchId());

        List<Player> players = teamPlayerRepository.findByMatchId(match.getMatchId());
        players.forEach(player -> System.out.println(player.getNickname()));

        match.setStatus(MatchStatus.STARTED);
        matchRepository.save(match);
    }


    public void initializeNumber(UUID matchId) {
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
