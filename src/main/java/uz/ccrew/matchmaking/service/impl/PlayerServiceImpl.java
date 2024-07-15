package uz.ccrew.matchmaking.service.impl;

import org.springframework.data.domain.*;
import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.dto.player.PlayerUpdateDTO;
import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.mapper.PlayerMapper;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.service.PlayerService;
import uz.ccrew.matchmaking.util.AuthUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final AuthUtil authUtil;

    @Override
    public PlayerDTO createPlayer(PlayerCreateDTO playerCreateDTO) {
        User user = authUtil.loadLoggedUser();
        Player player = playerMapper.toEntity(playerCreateDTO);
        player.setUserId(user.getId());
        playerRepository.save(player);
        return playerMapper.toDTO(player);
    }

    @Override
    public PlayerDTO updatePlayer(PlayerUpdateDTO playerUpdateDTO){
        User user = authUtil.loadLoggedUser();
        Player player = playerRepository.loadById(user.getId());
        player.setNickname(playerUpdateDTO.nickname());
        playerRepository.save(player);
        return playerMapper.toDTO(player);
    }
    @Override
    public void deletePlayer() {
        User user = authUtil.loadLoggedUser();
        playerRepository.deleteById(user.getId());
    }
    @Override
    public Page<PlayerDTO> getPlayersByNicknameLike(String nickname, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());
        Page<Player> pageObj = playerRepository.findByNicknameLike(nickname, pageable);
        List<PlayerDTO> dtoList = pageObj.getContent().stream().map(playerMapper::toDTO).toList();
        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }


    @Override
    public PlayerDTO getPlayerById(Integer id) {
        Player player = playerRepository.loadById(id);
        return playerMapper.toDTO(player);
    }

    @Override
    public Page<PlayerDTO> getAllPlayers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());

        Page<Player> pageObj = playerRepository.findAll(pageable);

        List<Player> playerList = pageObj.getContent();
        List<PlayerDTO> dtoList = playerList.stream().map(playerMapper::toDTO).toList();

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }
}