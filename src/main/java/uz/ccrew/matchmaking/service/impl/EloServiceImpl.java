package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.service.EloService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EloServiceImpl implements EloService {
    private final PlayerRepository playerRepository;

    @Override
    public void updateRatings(Player winner, Player loser) {
        int winnerRating = winner.getPoints();
        int loserRating = loser.getPoints();

        int newWinnerRating = calculateNewRating(winnerRating, loserRating, true);
        if (loser.getPoints() == 0){
            loser.setPoints(0);
        }else {
            int newLoserRating = calculateNewRating(loserRating, winnerRating, false);
            loser.setPoints(newLoserRating);
        }

        winner.setPoints(newWinnerRating);

        playerRepository.save(winner);
        playerRepository.save(loser);
    }

    @Override
    public void updateRatings(List<Player> winners,List<Player> losers) {
        for (int i = 0; i < winners.size(); i++) {
            updateRatings(winners.get(i), losers.get(i));
        }
    }

    private int calculateNewRating(int playerRating, int opponentRating, boolean isWinner) {
        double kFactor = 32;
        double expectedScore = 1 / (1 + Math.pow(10, (opponentRating - playerRating) / 400.0));
        double actualScore = isWinner ? 1 : 0;

        return (int) (playerRating + kFactor * (actualScore - expectedScore));
    }
}
