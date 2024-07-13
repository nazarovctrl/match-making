package uz.ccrew.matchmaking.dto.player;

import lombok.Builder;
import uz.ccrew.matchmaking.enums.Rank;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PlayerDTO")
@Builder
public record PlayerDTO(@NotBlank(message = "Nickname must not be blank") String nickname,
                        @NotBlank(message = "Rank must not be blank") Rank rank,
                        @NotBlank(message = "Points must not be blank") Integer points){}