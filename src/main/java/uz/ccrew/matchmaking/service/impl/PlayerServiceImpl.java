package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
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
    public PlayerDTO createPlayer(PlayerCreateDTO playerCreateDTO) {
        User user = authUtil.loadLoggedUser();
        Player player = playerMapper.toEntity(playerCreateDTO);
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
        playerRepository.deleteById(user.getId());
    }
    @Override
    public List<PlayerDTO> getPlayersByNicknameLike(String nickname) {
        List<Player> players = playerRepository.findByNicknameLike(nickname);
        return playerMapper.toDTOList(players);
    }

    @Override
    public PlayerDTO getPlayerById(Integer id) {
        Player player = playerRepository.loadById(id);
        return playerMapper.toDTO(player);
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        List<Player> list = playerRepository.findAll();
        return playerMapper.toDTOList(list);
    }
}