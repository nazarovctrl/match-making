package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.exp.PlayerNotFoundException;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.service.PlayerService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    public Player getPlayerByNickname(String nickname) {
        return playerRepository.findByNickname(nickname).orElseThrow(()-> new PlayerNotFoundException(nickname));
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}