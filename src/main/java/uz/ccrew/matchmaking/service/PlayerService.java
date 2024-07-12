package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.entity.Player;

import java.util.List;

public interface PlayerService {
    Player createPlayer(Player player);

    Player updatePlayer(Player player);

    void deletePlayer(Player player);

    Player getPlayerByNickname(String nickname);

    List<Player> getAllPlayers();
}