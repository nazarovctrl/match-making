package uz.ccrew.matchmaking.service;


import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.dto.player.PlayerUpdateDTO;

import org.springframework.data.domain.Page;

public interface PlayerService {
    PlayerDTO create(PlayerCreateDTO playerDTO);

    PlayerDTO update(PlayerUpdateDTO playerUpdateDTO);

    void delete();

    Page<PlayerDTO> getByNicknameLike(String nickname, int page, int size);

    PlayerDTO getById(Integer id);

    Page<PlayerDTO> getAll(int page, int size);
}