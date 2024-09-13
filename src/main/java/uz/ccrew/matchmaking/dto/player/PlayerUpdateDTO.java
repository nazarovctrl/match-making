package uz.ccrew.matchmaking.dto.player;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PlayerUpdateDTO")
public record PlayerUpdateDTO(@NotBlank(message = "Nickname must not be blank") String nickname){}