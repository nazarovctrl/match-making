package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.entity.LobbyPlayer;
import uz.ccrew.matchmaking.dto.lobby.LobbyPlayerDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LobbyPlayerMapper implements Mapper<Object, LobbyPlayerDTO, LobbyPlayer> {
    private final PlayerMapper playerMapper;

    @Override
    public LobbyPlayer toEntity(Object o) {
        return null;
    }

    @Override
    public LobbyPlayerDTO toDTO(LobbyPlayer dto) {
        return LobbyPlayerDTO.builder()
                .player(playerMapper.toDTO(dto.getPlayer()))
                .isLeader(dto.getIsLeader())
                .build();
    }
}
