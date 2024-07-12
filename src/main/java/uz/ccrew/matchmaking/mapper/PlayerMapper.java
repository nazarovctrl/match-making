package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.entity.Player;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerMapper implements Mapper<PlayerDTO, Player> {
    @Override
    public Player toEntity(PlayerDTO playerDTO) {
        return Player.builder()
                .user(playerDTO.user())
                .nickname(playerDTO.nickname())
                .rank(playerDTO.rank())
                .points(playerDTO.points())
                .build();
    }

    @Override
    public PlayerDTO toDTO(Player player) {
        return PlayerDTO.builder()
                .user(player.getUser())
                .nickname(player.getNickname())
                .rank(player.getRank())
                .points(player.getPoints())
                .build();
    }

    public List<PlayerDTO> toDTOList(List<Player> players) {
        return players.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}