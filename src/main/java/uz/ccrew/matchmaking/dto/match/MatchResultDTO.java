package uz.ccrew.matchmaking.dto.match;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(name = "MatchResultDTO")
public record MatchResultDTO(
        @NotBlank
        String matchId,
        @NotNull
        List<TeamResult> teamResults) {
}
