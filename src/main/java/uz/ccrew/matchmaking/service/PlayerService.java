package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.dto.player.PlayerUpdateDTO;

import java.util.List;

public interface PlayerService {
    PlayerDTO createPlayer(PlayerCreateDTO playerDTO);

    PlayerDTO updatePlayer(PlayerUpdateDTO playerUpdateDTO);

    void deletePlayer();

    List<PlayerDTO> getPlayersByNicknameLike(String nickname);

    PlayerDTO getPlayerById(Integer id);

    List<PlayerDTO> getAllPlayers();
}