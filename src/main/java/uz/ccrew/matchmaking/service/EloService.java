package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.entity.Player;

import java.util.List;

public interface EloService {
    void updateRatings(Player winner, Player loser);
    void updateRatings(List<Player> winners,List<Player> losers);
}
