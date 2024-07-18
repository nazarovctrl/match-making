package uz.ccrew.matchmaking.dto.server;

import jakarta.validation.constraints.NotBlank;

public record ServerCreateDTO(
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotBlank
        String name,
        @NotBlank
        String location){}