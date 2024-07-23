package uz.ccrew.matchmaking.dto.lobby;

import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;

import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LobbyDTO(String lobbyId,
                       TeamType teamType,
                       MatchMode matchMode,
                       List<LobbyPlayerDTO> players) {
}
