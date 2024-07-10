package uz.ccrew.matchmaking.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "Request for Login")
public class JwtRequest {

    @Schema(description = "email", example = "johndoe@gmail.com")
    @NotNull(message = "login must be not null.")
    private String login;

    @Schema(description = "password", example = "12345")
    @NotNull(message = "Password must be not null.")
    private String password;
}
