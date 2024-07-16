package uz.ccrew.matchmaking.dto.server;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ServerDTO(
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotBlank
        String name,
        @NotBlank
        String location,
        Boolean isBusy) {
}