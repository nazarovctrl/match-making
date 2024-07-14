package uz.ccrew.matchmaking.security.user;

import uz.ccrew.matchmaking.dto.auth.RegisterDTO;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.service.AuthService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    void register(String login) {
        RegisterDTO registerDTO = new RegisterDTO(login, "200622az");
        authService.register(registerDTO);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void loadUserByUsername() {
        String login = "Azimjon";
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(login));

        register(login);

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        assertNotNull(userDetails);
        assertEquals(login, userDetails.getUsername());
    }
}