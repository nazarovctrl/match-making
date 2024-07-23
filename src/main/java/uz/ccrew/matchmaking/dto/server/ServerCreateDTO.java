package uz.ccrew.matchmaking.dto.server;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(name = "ServerCreateDTO")
public record ServerCreateDTO(
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotBlank
        String name,
        @NotBlank
        String location){}