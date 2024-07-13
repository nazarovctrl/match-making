package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.entity.Player;

import java.util.List;

public interface PlayerService {
    PlayerDTO createPlayer(PlayerDTO playerDTO);

    PlayerDTO updatePlayer(PlayerDTO playerDTO);

    void deletePlayer();

    PlayerDTO getPlayerByNickname(String nickname);

    PlayerDTO getPlayerById(Integer id);

    List<PlayerDTO> getAllPlayers();
}