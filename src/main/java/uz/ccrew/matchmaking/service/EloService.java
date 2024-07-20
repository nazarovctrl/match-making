package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.entity.Player;

import java.util.List;

public interface EloService {
    public void updateRatings(Player winner, Player loser);
    public void updateRatings(List<Player> winners,List<Player> losers);
}
