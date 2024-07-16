package uz.ccrew.matchmaking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ccrew.matchmaking.dto.lobby.LobbyCreateDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyDTO;
import uz.ccrew.matchmaking.entity.Lobby;

@Component
@RequiredArgsConstructor
public class LobbyMapper implements Mapper<LobbyCreateDTO, LobbyDTO, Lobby> {
    private final PlayerMapper playerMapper;

    @Override
    public Lobby toEntity(LobbyCreateDTO createDTO) {
        return Lobby.builder()
                .matchMode(createDTO.matchMode())
                .teamType(createDTO.teamType())
                .build();
    }

    @Override
    public LobbyDTO toDTO(Lobby lobby) {
        return LobbyDTO.builder().id(lobby.getId())
                .matchMode(lobby.getMatchMode())
                .teamType(lobby.getTeamType())
                .players(playerMapper.toDTOList(lobby.getPlayers()))
                .build();
    }
}
