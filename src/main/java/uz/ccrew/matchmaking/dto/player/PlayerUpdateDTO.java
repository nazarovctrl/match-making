package uz.ccrew.matchmaking.dto.player;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PlayerUpdateDTO(@NotBlank(message = "Nickname must not be blank") String nickname){}