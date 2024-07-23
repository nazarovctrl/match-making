package uz.ccrew.matchmaking.dto.match;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TeamResult")
public record TeamResult(
        @NotBlank
        String teamId,
        @NotNull
        Integer place) {
}
