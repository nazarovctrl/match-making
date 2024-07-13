package uz.ccrew.matchmaking.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request for Login")
public record LoginDTO(@NotBlank(message = "login must be not blank.")
                       @Schema(description = "email", example = "johndoe@gmail.com")
                       String login,
                       @NotBlank(message = "password must be not blank.")
                       @Schema(description = "password", example = "12345")
                       String password) {
}