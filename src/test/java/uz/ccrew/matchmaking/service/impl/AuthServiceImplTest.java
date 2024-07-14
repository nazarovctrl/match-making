package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;
import uz.ccrew.matchmaking.dto.auth.RegisterDTO;
import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.exp.AlreadyExistException;
import uz.ccrew.matchmaking.mapper.UserMapper;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.security.user.UserDetailsServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceImplTest {
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    void register() {
        RegisterDTO registerDTO = new RegisterDTO("Azimjon", "200622az");
        AtomicReference<UserDTO> userDTOAtomic = new AtomicReference<>();
        assertDoesNotThrow(() -> userDTOAtomic.set(authService.register(registerDTO)));

        UserDTO result = userDTOAtomic.get();
        assertNotNull(result.id());
        assertEquals(result.login(), registerDTO.login());
        assertEquals(result.role(), UserRole.PLAYER);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        assertDoesNotThrow(() -> atomicReference.set(userRepository.loadById(result.id())));

        User user = atomicReference.get();
        assertNotNull(user.getCredentialsModifiedDate());

        UserDTO userDTO = userMapper.mapEntity(user);
        assertEquals(result, userDTO);
    }

    @Test
    void registerWithAlreadyExistLogin() {
        RegisterDTO dto = new RegisterDTO("Azimjon", "200622az");
        assertThrows(AlreadyExistException.class, () -> authService.register(dto));
    }

    @Test
    void login() {
        LoginDTO loginDTO = new LoginDTO("Azimjon", "200622az");
        AtomicReference<LoginResponseDTO> resultAtomic = new AtomicReference<>();
        assertDoesNotThrow(() -> resultAtomic.set(authService.login(loginDTO)));

        LoginResponseDTO result = resultAtomic.get();
        assertNotNull(result);
        assertNotNull(result.accessToken());
        assertNotNull(result.refreshToken());
    }

    @Test
    void loginWithBadCredentials() {
        LoginDTO loginDTO = new LoginDTO("Alex", "200623az");
        assertThrows(BadCredentialsException.class, () -> authService.login(loginDTO));
    }

    @Test
    void refresh() {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("Azimjon");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = authService.refresh();
        assertNotNull(accessToken);
    }
}