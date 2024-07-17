package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.dto.lobby.LobbyCreateDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyDTO;
import uz.ccrew.matchmaking.entity.Lobby;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LobbyMapper implements Mapper<LobbyCreateDTO, LobbyDTO, Lobby> {
    @Override
    public Lobby toEntity(LobbyCreateDTO createDTO) {
        return Lobby.builder()
                .matchMode(createDTO.matchMode())
                .teamType(createDTO.teamType())
                .build();
    }

    @Override
    public LobbyDTO toDTO(Lobby lobby) {
        return LobbyDTO.builder()
                .lobbyId(lobby.getId().toString())
                .matchMode(lobby.getMatchMode())
                .teamType(lobby.getTeamType())
                .build();
    }
}
