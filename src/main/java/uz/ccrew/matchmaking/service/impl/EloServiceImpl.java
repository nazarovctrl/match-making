package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.Team;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.service.EloService;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.repository.TeamPlayerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EloServiceImpl implements EloService {
    private final PlayerRepository playerRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    @Override
    public void updateRating(Player winner, Player loser) {
        double player1Expected = calculateExpectedScore(winner.getPoints(), loser.getPoints());
        double player2Expected = calculateExpectedScore(loser.getPoints(), winner.getPoints());

        double winnerNewPoint = calculateRating(winner.getPoints(), player1Expected, 1);
        double loserNewPoint = calculateRating(loser.getPoints(), player2Expected, 0);
        winner.setPoints((int) winnerNewPoint);
        loser.setPoints((int) loserNewPoint);

        playerRepository.save(winner);
        playerRepository.save(loser);
    }

    @Override
    public void updateFFARatings(List<Team> teams) {
        int totalPlayers = teams.size();

        for (Team team : teams) {
            Player player = teamPlayerRepository.findByTeamId(team.getTeamId()).getFirst();

            double expectedSum = 0;
            double actualSum = 0;

            for (Team opponentTeam : teams) {
                if (team.equals(opponentTeam)) {
                    continue;
                }
                Player opponent = teamPlayerRepository.findByTeamId(team.getTeamId()).getFirst();

                expectedSum += calculateExpectedScore(player.getPoints(), opponent.getPoints());

                if (team.getPlacement() < opponentTeam.getPlacement()) {
                    actualSum += 1;
                } else if (team.getPlacement().equals(opponentTeam.getPlacement())) {
                    actualSum += 0.5;
                }
            }

            double calculatedPoints = calculateRating(player.getPoints(), expectedSum / (totalPlayers - 1), actualSum / (totalPlayers - 1));
            player.setPoints((int) calculatedPoints);
        }
    }

    @Override
    public void updateTeamRatings(List<Player> winners, List<Player> losers) {
        double team1Rating = calculateTeamRating(winners);
        double team2Rating = calculateTeamRating(losers);

        double team1Expected = calculateExpectedScore(team1Rating, team2Rating);
        double team2Expected = calculateExpectedScore(team2Rating, team1Rating);

        for (Player winner : winners) {
            double calculateRating = calculateRating(winner.getPoints(), team1Expected, 1);
            winner.setPoints((int) calculateRating);
        }

        for (Player loser : losers) {
            double calculateRating = calculateRating(loser.getPoints(), team2Expected, 0);
            loser.setPoints((int) calculateRating);
        }
    }

    @Override
    public void updateFFATeamRatings(List<Team> teams) {
        for (Team team : teams) {
            List<Player> players = teamPlayerRepository.findByTeamId(team.getTeamId());
            double teamRating = calculateTeamRating(players);
            double expectedSum = 0;
            double actualSum = 0;

            for (Team opponentTeam : teams) {
                if (team.getTeamId().equals(opponentTeam.getTeamId())) {
                    continue;
                }

                List<Player> opponents = teamPlayerRepository.findByTeamId(team.getTeamId());
                double opponentTeamRating = calculateTeamRating(opponents);
                expectedSum += calculateExpectedScore(teamRating, opponentTeamRating);

                if (team.getPlacement() < opponentTeam.getPlacement()) {
                    actualSum += 1;
                } else if (team.getPlacement().equals(opponentTeam.getPlacement())) {
                    actualSum += 0.5;
                }

                for (Player player : players) {
                    double calculatedRating = calculateRating(player.getPoints(),
                            expectedSum / (teams.size() - 1),
                            actualSum / (teams.size() - 1));

                    player.setPoints((int) calculatedRating);
                }
            }
        }
    }


    private double calculateRating(double currentRating, double expectedScore, double actualScore) {
        return currentRating + 32 * (actualScore - expectedScore);
    }

    private double calculateExpectedScore(double playerRating, double opponentRating) {
        return 1 / (1 + Math.pow(10, (opponentRating - playerRating) / 400));
    }

    private double calculateTeamRating(List<Player> players) {
        double sum = 0;
        for (Player player : players) {
            sum += player.getPoints();
        }
        return sum / players.size();
    }
}
