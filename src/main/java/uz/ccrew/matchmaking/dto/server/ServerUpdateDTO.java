package uz.ccrew.matchmaking.dto.server;

import jakarta.validation.constraints.NotBlank;

public record ServerUpdateDTO(
        @NotBlank(message = "Name must not be blank")
        String name,
        @NotBlank(message = "Location must not be blank")
        String location) {
}