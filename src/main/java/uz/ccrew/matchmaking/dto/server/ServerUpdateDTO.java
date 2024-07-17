package uz.ccrew.matchmaking.dto.server;

import jakarta.validation.constraints.NotBlank;

public record ServerUpdateDTO(
        @NotBlank
        String name,
        @NotBlank
        String location){}