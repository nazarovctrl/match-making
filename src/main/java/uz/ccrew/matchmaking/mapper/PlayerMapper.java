package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.enums.Rank;

import org.springframework.stereotype.Component;

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
                .points(0)
                .rank(Rank.BRONZE)
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
}