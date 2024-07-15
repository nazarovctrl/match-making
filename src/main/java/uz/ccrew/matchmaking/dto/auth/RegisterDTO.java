package uz.ccrew.matchmaking.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for Registration")
public record RegisterDTO(@NotBlank(message = "Login must not be blank")
                          @Schema(description = "login", example = "john")
                          String login,
                          @NotBlank(message = "Login must not be blank")
                          @Schema(description = "password", example = "12345")
                          String password) {
}
