package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.dto.player.PlayerUpdateDTO;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.mapper.PlayerMapper;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.service.PlayerService;
import uz.ccrew.matchmaking.util.AuthUtil;

import org.springframework.data.domain.*;
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
    public PlayerDTO create(PlayerCreateDTO playerCreateDTO) {
        User user = authUtil.loadLoggedUser();

        Player player = playerMapper.toEntity(playerCreateDTO);
        player.setPlayerId(user.getId());
        playerRepository.save(player);
        return playerMapper.toDTO(player);
    }

    @Override
    public PlayerDTO update(PlayerUpdateDTO playerUpdateDTO) {
        User user = authUtil.loadLoggedUser();

        Player player = playerRepository.loadById(user.getId());
        player.setNickname(playerUpdateDTO.nickname());
        playerRepository.save(player);
        return playerMapper.toDTO(player);
    }

    @Override
    public void delete() {
        User user = authUtil.loadLoggedUser();
        playerRepository.deleteById(user.getId());
    }

    @Override
    public Page<PlayerDTO> getByNicknameLike(String nickname, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("points").descending());

        Page<Player> pageObj = playerRepository.findByNicknameLike(nickname, pageable);
        List<PlayerDTO> dtoList = playerMapper.toDTOList(pageObj.getContent());

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    @Override
    public PlayerDTO getById(Integer id) {
        Player player = playerRepository.loadById(id);
        return playerMapper.toDTO(player);
    }

    @Override
    public Page<PlayerDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("points").descending());

        Page<Player> pageObj = playerRepository.findAll(pageable);

        List<Player> playerList = pageObj.getContent();
        List<PlayerDTO> dtoList = playerMapper.toDTOList(playerList);

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }
}