package uz.ccrew.matchmaking.dto.lobby;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.TeamType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for create lobby")
public record LobbyCreateDTO(
        @NotNull(message = "Team type must not be null")
        TeamType teamType,
        @NotNull(message = "Match mode must not be null")
        MatchMode matchMode) {
}
