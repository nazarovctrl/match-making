package uz.ccrew.matchmaking.service.impl;


import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.entity.Server;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.mapper.ServerMapper;
import uz.ccrew.matchmaking.repository.ServerRepository;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.service.ServerService;
import uz.ccrew.matchmaking.util.AuthUtil;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;
    private final ServerMapper serverMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthUtil authUtil;

    @Transactional
    @Override
    public ServerDTO create(ServerDTO dto) {
        User user = User.builder()
                .login(dto.login())
                .password(passwordEncoder.encode(dto.password()))
                .role(UserRole.SERVER)
                .build();
        userRepository.save(user);

        Server server = serverMapper.toEntity(dto);

        server.setUser(user);
        serverRepository.save(server);

        return serverMapper.toDTO(server);
    }

    @Override
    public ServerDTO update(ServerDTO dto) {
        User user = User.builder()
                .login(dto.login())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        userRepository.save(user);

        Server server = serverMapper.toEntity(dto);

        server.setUser(user);
        serverRepository.save(server);

        return serverMapper.toDTO(server);
    }

    @Override
    public void delete(Integer id) {
        Server server = serverRepository.loadById(id);
        serverRepository.delete(server);
    }

    @Override
    public ServerDTO findById(Integer id) {
        return serverMapper.toDTO(serverRepository.findById(id).get());
    }
}