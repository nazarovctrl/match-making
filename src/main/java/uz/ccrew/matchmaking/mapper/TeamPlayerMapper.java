package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.entity.TeamPlayer;
import uz.ccrew.matchmaking.dto.match.TeamPlayerDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamPlayerMapper implements Mapper<Object, TeamPlayerDTO, TeamPlayer> {
    private final PlayerMapper playerMapper;

    @Override
    public TeamPlayer toEntity(Object o) {
        return null;
    }

    @Override
    public TeamPlayerDTO toDTO(TeamPlayer teamPlayer) {
        return TeamPlayerDTO.builder()
                .number(teamPlayer.getNumber())
                .player(playerMapper.toDTO(teamPlayer.getPlayer()))
                .build();
    }
}
