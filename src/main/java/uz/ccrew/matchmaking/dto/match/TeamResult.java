package uz.ccrew.matchmaking.dto.match;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "TeamResult")
public record TeamResult(
        @NotBlank
        String teamId,
        @NotNull
        Integer place) {
}
