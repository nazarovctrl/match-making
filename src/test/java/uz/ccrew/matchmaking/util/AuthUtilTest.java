package uz.ccrew.matchmaking.util;

import uz.ccrew.matchmaking.dto.auth.RegisterDTO;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.exp.unauthorized.Unauthorized;
import uz.ccrew.matchmaking.service.AuthService;
import uz.ccrew.matchmaking.security.user.UserDetailsServiceImpl;

import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class AuthUtilTest {
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthService authService;

    void register(String login) {
        RegisterDTO registerDTO = new RegisterDTO(login, "200622az");
        authService.register(registerDTO);
    }

    void setAuthentication(String login) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(login);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void takeLoggedUser() {
        Optional<User> optional = authUtil.takeLoggedUser();
        assertTrue(optional.isEmpty());

        String login = "Azimjon";
        register(login);
        setAuthentication(login);

        optional = authUtil.takeLoggedUser();
        assertTrue(optional.isPresent());

        User user = optional.get();
        assertNotNull(user.getId());
        assertEquals(login, user.getLogin());
    }

    @Test
    void loadLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);

        assertThrows(Unauthorized.class, () -> authUtil.loadLoggedUser());

        String login = "Azimjon";
        setAuthentication(login);

        User user = authUtil.loadLoggedUser();
        assertNotNull(user.getId());
        assertEquals(login, user.getLogin());
    }
}