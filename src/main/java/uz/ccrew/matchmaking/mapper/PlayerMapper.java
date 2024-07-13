package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.entity.Player;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerMapper implements Mapper<PlayerDTO, Player> {
    @Override
    public Player toEntity(PlayerDTO dto) {
        return Player.builder()
                .nickname(dto.nickname())
                .rank(dto.rank())
                .points(dto.points())
                .build();
    }

    public Player toEntity(PlayerCreateDTO dto) {
        return Player.builder()
                .nickname(dto.nickname())
                .build();
    }

    @Override
    public PlayerDTO toDTO(Player player) {
        return PlayerDTO.builder()
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