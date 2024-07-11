package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.entity.Server;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.mapper.ServiceMapper;
import uz.ccrew.matchmaking.repository.ServerRepository;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.service.ServerService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {
    private final ServerRepository repository;
    private final ServiceMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ServerDTO add(ServerDTO dto) {
        User user = User.builder()
                .login(dto.login())
                .password(passwordEncoder.encode(dto.password()))
                .role(UserRole.SERVER)
                .build();
        userRepository.save(user);
        Server server = mapper.mapDTO(dto);
        server.setUser(user);
        repository.save(server);

        return mapper.mapEntity(server);
    }
}
