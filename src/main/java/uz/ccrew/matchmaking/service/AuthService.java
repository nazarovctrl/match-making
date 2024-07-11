package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.RegisterDTO;
import uz.ccrew.matchmaking.dto.UserDTO;
import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginResponseDTO login(LoginDTO loginRequest);
    String refresh(HttpServletRequest request);
    UserDTO register(RegisterDTO dto);
}
