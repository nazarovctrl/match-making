package uz.ccrew.matchmaking.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(description = "Request for Login")
public class LoginDTO {
    @Schema(description = "email", example = "johndoe@gmail.com")
    @NotBlank(message = "login must be not blank.")
    private String login;

    @Schema(description = "password", example = "12345")
    @NotBlank(message = "Password must be not blank.")
    private String password;
}
