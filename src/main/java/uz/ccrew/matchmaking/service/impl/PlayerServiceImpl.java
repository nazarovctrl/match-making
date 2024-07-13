package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.exp.PlayerNotFoundException;
import uz.ccrew.matchmaking.mapper.PlayerMapper;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.service.PlayerService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ccrew.matchmaking.util.AuthUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final AuthUtil authUtil;

    @Override
    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        User user = authUtil.loadLoggedUser();
        Player player = playerMapper.toEntity(playerDTO);
        player.setUser(user);
        playerRepository.save(player);
        return playerMapper.toDTO(player);
    }

    @Override
    public PlayerDTO updatePlayer(PlayerDTO playerDTO) {
        User user = authUtil.loadLoggedUser();
        Player player = playerMapper.toEntity(playerDTO);
        player.setUser(user);
        playerRepository.save(player);
        return playerMapper.toDTO(player);
    }
    @Override
    public void deletePlayer() {
        User user = authUtil.loadLoggedUser();
        playerRepository.deleteById(user);
    }
    @Override
    public PlayerDTO getPlayerByNickname(String nickname) {
        Player player = playerRepository.findByNickname(nickname).orElseThrow(()->new PlayerNotFoundException("Players with this " + nickname +" does not exists"));
        return playerMapper.toDTO(player);
    }

    @Override
    public PlayerDTO getPlayerById(Integer id) {
//        Player player = playerRepository.findById(id).orElseThrow(()->new PlayerNotFoundException("Player is not found"));
        return playerMapper.toDTO(null);
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        List<Player> list = playerRepository.findAll();
        return playerMapper.toDTOList(list);
    }
}