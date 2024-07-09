package uz.ccrew.matchmaking.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
/**
 * Data Transfer Object (DTO) representing a request for JWT (JSON Web Token) authentication.
 */

@Data
@Schema(description = "Request for Login")
public class JwtRequest {
    /**
     * Username entered during authentication.
     */
    @Schema(description = "email", example = "johndoe@gmail.com")
    @NotNull(message = "login must be not null.") // он требует валидации, так как мы туда отправляем данные
    private String login;
    /**
     * Password entered during authentication.
     */
    @Schema(description = "password", example = "12345")
    @NotNull(message = "Password must be not null.")
    private String password;
}
