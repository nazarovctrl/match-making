package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.RegisterDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;

public interface AuthService {
    UserDTO register(RegisterDTO dto);

    LoginResponseDTO login(LoginDTO loginRequest);

    String refresh();
}
