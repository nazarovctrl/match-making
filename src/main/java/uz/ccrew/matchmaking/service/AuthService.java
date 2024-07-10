package uz.ccrew.matchmaking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import uz.ccrew.matchmaking.dto.auth.JwtRequest;
import uz.ccrew.matchmaking.dto.auth.JwtResponse;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.security.JwtTokenProvider;

import java.nio.file.AccessDeniedException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    public JwtResponse login(final JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLogin(),
                            loginRequest.getPassword()
                    )
            );
            User user = userService.getByLogin(loginRequest.getLogin());
            jwtResponse.setId(user.getId());
            jwtResponse.setLogin(user.getLogin());
            jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                    (user.getId()),
                    user.getLogin(),
                    Collections.singleton(user.getRole())
            ));
            jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                    user.getId(),
                    user.getLogin()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jwtResponse;
    }


    public JwtResponse refresh(final String refreshToken) throws AccessDeniedException {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
