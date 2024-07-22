package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.entity.Team;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.repository.TeamPlayerRepository;
import uz.ccrew.matchmaking.service.EloService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EloServiceImpl implements EloService {
    private final PlayerRepository playerRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    @Override
    public void updateRatings(Player winner, Player loser) {
        int winnerRating = winner.getPoints();
        int loserRating = loser.getPoints();

        int newWinnerRating = calculateNewRating(winnerRating, loserRating, true);
        winner.setPoints(newWinnerRating);
        playerRepository.save(winner);

        if (loser.getPoints() != 0) {
            int newLoserRating = calculateNewRating(loserRating, winnerRating, false);
            loser.setPoints(newLoserRating);
            playerRepository.save(loser);
        }
    }


//    @Override
//    public void updateRatings(List<Player> winners,List<Player> losers) {
//        for (int i = 0; i < winners.size(); i++) {
//            updateRatings(winners.get(i), losers.get(i));
//        }
//    }

    @Override
    public void updateRatings(List<Team> teams) {
        int size = teams.size();
        for (Team team : teams) {
            Player player = teamPlayerRepository.findByTeamId(team.getTeamId()).getFirst();

            int playerRating = player.getPoints();

            double actualScore = calculateActualScore(size,team.getPlace());
            double expectedScore = 0;

            for (Team opponentTeam: teams){
                if (opponentTeam.getTeamId().equals(team.getTeamId())){
                    continue;
                }
                Player opponentRating = teamPlayerRepository.findByTeamId(opponentTeam.getTeamId()).getFirst();
                expectedScore += calculateExpectedScore(playerRating, opponentRating.getPoints());
            }
            expectedScore /= (size - 1);

            int newRating = calculateNewRating(playerRating, expectedScore, actualScore);
            player.setPoints(newRating);
        }
    }


    private int calculateNewRating(int playerRating, int opponentRating, boolean isWinner) {
        int kFactor = 32;
        int expectedScore = (int) (1 / (1 + Math.pow(10, (opponentRating - playerRating) / 400.0)));
        int actualScore = isWinner ? 1 : 0;

        return (int) (playerRating + kFactor * (actualScore - expectedScore));
    }


    private double calculateExpectedScore(int playerRating, int opponentRating) {
        return 1 / (1 + Math.pow(10, (opponentRating - playerRating) / 400.0));
    }

    private double calculateActualScore(int numPlayers, int playerRanking) {
        return (numPlayers - playerRanking) / (double) (numPlayers - 1);
    }

    private int calculateNewRating(int playerRating, double expectedScore, double actualScore) {
        double kFactor = 32;
        return (int) (playerRating + kFactor * (actualScore - expectedScore));
    }
}
