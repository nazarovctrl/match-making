package uz.ccrew.matchmaking.config;

import uz.ccrew.matchmaking.dto.auth.RegisterDTO;
import uz.ccrew.matchmaking.security.user.UserDetailsServiceImpl;
import uz.ccrew.matchmaking.service.AuthService;

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
class AuditorAwareImplTest {
    @Autowired
    private AuditorAwareImpl auditorAware;
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
    void getCurrentAuditor() {
        Optional<Integer> currentAuditor = auditorAware.getCurrentAuditor();
        assertTrue(currentAuditor.isEmpty());

        String login = "Azimjon";
        register(login);
        setAuthentication(login);

        currentAuditor = auditorAware.getCurrentAuditor();
        assertTrue(currentAuditor.isPresent());

        Integer id = currentAuditor.get();
        assertNotNull(id);

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}