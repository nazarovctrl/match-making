package uz.ccrew.matchmaking.service;


import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.dto.player.PlayerUpdateDTO;

import org.springframework.data.domain.Page;

public interface PlayerService {
    PlayerDTO createPlayer(PlayerCreateDTO playerDTO);

    PlayerDTO updatePlayer(PlayerUpdateDTO playerUpdateDTO);

    void deletePlayer();

    Page<PlayerDTO> getPlayersByNicknameLike(String nickname,int page, int size);

    PlayerDTO getPlayerById(Integer id);

    Page<PlayerDTO> getAllPlayers(int page, int size);
}