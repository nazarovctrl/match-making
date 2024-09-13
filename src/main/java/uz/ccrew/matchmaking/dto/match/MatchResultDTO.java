package uz.ccrew.matchmaking.dto.match;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "MatchResultDTO")
public record MatchResultDTO(
        @NotBlank
        String matchId,
        @NotNull
        List<TeamResult> teamResults) {
}
