package uz.ccrew.matchmaking.dto.player;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PlayerCreateDTO")
public record PlayerCreateDTO(@NotBlank(message = "Nickname must not be blank") String nickname){}