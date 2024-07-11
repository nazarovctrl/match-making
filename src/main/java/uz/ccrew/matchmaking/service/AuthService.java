package uz.ccrew.matchmaking.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uz.ccrew.matchmaking.dto.RegisterDTO;
import uz.ccrew.matchmaking.dto.UserDTO;
import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.exp.AuthHeaderNotFound;
import uz.ccrew.matchmaking.exp.TokenExpiredException;
import uz.ccrew.matchmaking.mapper.UserMapper;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.security.JWTService;
import uz.ccrew.matchmaking.security.UserDetailsImpl;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    public LoginResponseDTO login(final LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLogin(),
                            loginRequest.getPassword()));
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            LoginResponseDTO responseDTO = new LoginResponseDTO();
            responseDTO.setAccessToken(jwtService.generateAccessToken(userDetails.getUsername()));
            responseDTO.setRefreshToken(jwtService.generateRefreshToken(userDetails.getUsername()));
            return responseDTO;
    }

    public String refresh(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
         throw new AuthHeaderNotFound("AuthHeader Not Found");
        }
        String refreshToken = authHeader.substring(7);
        if (jwtService.isTokenExpired(refreshToken)){
            throw new TokenExpiredException(jwtService.getTokenExpiredMessage(refreshToken));
        }
        String login = jwtService.extractRefreshTokenLogin(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        return jwtService.generateAccessToken(refreshToken);
    }

    public UserDTO register(RegisterDTO dto) {
        Optional<User> optional = userRepository.findByLogin(dto.login());
        if (optional.isPresent()){
            throw new IllegalStateException("Username is already existing");
        }
        User user = new User(dto.login(),passwordEncoder.encode(dto.password()),UserRole.PLAYER);
        userRepository.save(user);
        return userMapper.mapEntity(user);
    }
}
