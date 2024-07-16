package uz.ccrew.matchmaking.dto.lobby;

import lombok.Builder;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.TeamType;

import java.util.List;

@Builder
public record LobbyDTO(String id,
                       TeamType teamType,
                       MatchMode matchMode,
                       List<PlayerDTO> players,
                       PlayerDTO leader) {
}
