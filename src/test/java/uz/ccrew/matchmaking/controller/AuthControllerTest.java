package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;
import uz.ccrew.matchmaking.dto.auth.RegisterDTO;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.service.AuthService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        RegisterDTO registerDTO = new RegisterDTO("Azimjon", "200622az");
        authService.register(registerDTO);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void register() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("Nazarov", "200622az");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.login").value(registerDTO.login()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(UserRole.PLAYER.name()));
    }

    @Test
    void login() throws Exception {
        LoginDTO loginDTO = new LoginDTO("Azimjon", "200622az");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.accessToken").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.refreshToken").isNotEmpty());
    }

    @Test
    void refresh() throws Exception {
        LoginDTO loginDTO = new LoginDTO("Azimjon", "200622az");
        LoginResponseDTO login = authService.login(loginDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/refresh")
                        .header("Authorization", "Bearer " + login.refreshToken()) // Set your authorization header here
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }
}
