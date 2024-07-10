package uz.ccrew.matchmaking.dto.auth;

import lombok.Data;

@Data
public class JwtResponse {

    private Integer id;

    private String login;

    private String accessToken;

    private String refreshToken;

}
