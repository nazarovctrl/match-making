package uz.ccrew.matchmaking.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.entity.Server;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.mapper.ServiceMapper;
import uz.ccrew.matchmaking.repository.ServerRepository;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.service.ServerService;

import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl implements ServerService {
    private final ServerRepository repository;
    private final ServiceMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ServerServiceImpl(ServerRepository repository, ServiceMapper mapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ServerDTO add(ServerDTO dto) {
        User user = new User(dto.login(), passwordEncoder.encode(dto.password()), UserRole.SERVER);
        userRepository.save(user);

        Server server = mapper.mapDTO(dto);

        server.setUser(user);
        repository.save(server);

        return mapper.mapEntity(server);
    }
}
