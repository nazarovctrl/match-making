package uz.ccrew.matchmaking.dto.player;

import jakarta.validation.constraints.NotBlank;

public record PlayerUpdateDTO(@NotBlank(message = "Nickname must not be blank") String nickname){}