package uz.ccrew.matchmaking.dto.server;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "ServerUpdateDTO")
public record ServerUpdateDTO(
        @NotBlank(message = "Name must not be blank")
        String name,
        @NotBlank(message = "Location must not be blank")
        String location){}