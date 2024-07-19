package uz.ccrew.matchmaking.dto.player;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "PlayerUpdateDTO")
public record PlayerUpdateDTO(@NotBlank(message = "Nickname must not be blank") String nickname){}