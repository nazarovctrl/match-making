package uz.ccrew.matchmaking.dto.auth;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String accessToken;

    private String refreshToken;
}
