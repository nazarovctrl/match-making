package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.RegisterDTO;
import uz.ccrew.matchmaking.dto.UserDTO;
import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.exp.AlreadyExistException;
import uz.ccrew.matchmaking.exp.AuthHeaderNotFound;
import uz.ccrew.matchmaking.exp.TokenExpiredException;
import uz.ccrew.matchmaking.mapper.UserMapper;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.security.jwt.JWTService;
import uz.ccrew.matchmaking.security.UserDetailsImpl;
import uz.ccrew.matchmaking.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @Override
    public LoginResponseDTO login(final LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.login(),
                        loginRequest.password()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        LoginResponseDTO responseDTO = new LoginResponseDTO(jwtService.generateAccessToken(userDetails.getUsername()),
                jwtService.generateRefreshToken(userDetails.getUsername()));
        return responseDTO;
    }

    @Override
    public String refresh(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthHeaderNotFound("AuthHeader Not Found");
        }
        String refreshToken = authHeader.substring(7);
        if (jwtService.isTokenExpired(refreshToken)) {
            throw new TokenExpiredException(jwtService.getTokenExpiredMessage(refreshToken));
        }

        String login = jwtService.extractRefreshTokenLogin(refreshToken);
        userDetailsService.loadUserByUsername(login);

        return jwtService.generateAccessToken(refreshToken);
    }

    @Override
    public UserDTO register(RegisterDTO dto) {
        Optional<User> optional = userRepository.findByLogin(dto.login());
        if (optional.isPresent()) {
            throw new AlreadyExistException("Username is already existing");
        }
        User user = User.builder().login(dto.login()).password(passwordEncoder.encode(dto.password())).role(UserRole.PLAYER).build();
        userRepository.save(user);
        return userMapper.mapEntity(user);
    }
}