package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.entity.Team;
import uz.ccrew.matchmaking.entity.Player;

import java.util.List;

public interface EloService {
    void updateRating(Player winner, Player loser);

    void updateFFARatings(List<Team> teams);

    void updateTeamRatings(List<Player> winners, List<Player> losers);

    void updateFFATeamRatings(List<Team> teams);
}
