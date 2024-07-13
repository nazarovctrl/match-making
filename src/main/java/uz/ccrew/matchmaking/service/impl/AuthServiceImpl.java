package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.auth.RegisterDTO;
import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.exp.AlreadyExistException;
import uz.ccrew.matchmaking.mapper.UserMapper;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.security.jwt.JWTService;
import uz.ccrew.matchmaking.security.user.UserDetailsImpl;
import uz.ccrew.matchmaking.service.AuthService;
import uz.ccrew.matchmaking.util.AuthUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthUtil authUtil;


    @Override
    public UserDTO register(RegisterDTO dto) {
        Optional<User> optional = userRepository.findByLogin(dto.login());
        if (optional.isPresent()) {
            throw new AlreadyExistException("Username is already existing");
        }
        User user = User.builder()
                .login(dto.login())
                .password(passwordEncoder.encode(dto.password()))
                .role(UserRole.PLAYER)
                .credentialsModifiedDate(new Date())
                .build();

        userRepository.save(user);
        return userMapper.mapEntity(user);
    }

    @Override
    public LoginResponseDTO login(final LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new LoginResponseDTO(jwtService.generateAccessToken(userDetails.getUsername()), jwtService.generateRefreshToken(userDetails.getUsername()));
    }

    @Override
    public String refresh() {
        User user = authUtil.loadLoggedUser();
        return jwtService.generateAccessToken(user.getLogin());
    }
}