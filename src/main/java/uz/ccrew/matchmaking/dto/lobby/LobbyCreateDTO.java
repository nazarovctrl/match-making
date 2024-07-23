package uz.ccrew.matchmaking.dto.lobby;

import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for create lobby")
public record LobbyCreateDTO(
        @NotNull(message = "Team type must not be null")
        TeamType teamType,
        @NotNull(message = "Match mode must not be null")
        MatchMode matchMode) {
}
