package uz.ccrew.matchmaking.dto.server;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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