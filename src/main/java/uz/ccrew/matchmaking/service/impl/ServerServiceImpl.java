package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.server.ServerCreateDTO;
import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.dto.server.ServerUpdateDTO;
import uz.ccrew.matchmaking.entity.Server;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.exp.NotFoundException;
import uz.ccrew.matchmaking.mapper.ServerMapper;
import uz.ccrew.matchmaking.repository.ServerRepository;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.service.ServerService;
import uz.ccrew.matchmaking.util.AuthUtil;

import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ServerDTO create(ServerCreateDTO dto) {
        User user = User.builder()
                .login(dto.login())
                .password(passwordEncoder.encode(dto.password()))
                .role(UserRole.SERVER)
                .build();
        userRepository.save(user);

        Server server = serverMapper.toEntity(dto);
        server.setServerId(user.getId());
        serverRepository.save(server);

        return serverMapper.toDTO(server);
    }

    @Override
    public ServerDTO update(ServerUpdateDTO dto,Integer id) {
        Server server = serverRepository.loadById(id);

        server.setName(dto.name());
        server.setLocation(dto.location());
        server.setServerId(server.getServerId());
        serverRepository.save(server);

        return serverMapper.toDTO(server);
    }

    @Override
    public void delete(Integer id) {
        Server server = serverRepository.loadById(id);
        serverRepository.delete(server);
    }

    @Override
    public ServerDTO getById(Integer id) {
        Server server =  serverRepository.loadById(id);
        return serverMapper.toDTO(server);
    }

    @Override
    public Page<ServerDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("serverId").descending());

        Page<Server> pageObj = serverRepository.findAll(pageable);

        List<Server> playerList = pageObj.getContent();
        List<ServerDTO> dtoList = serverMapper.toDTOList(playerList);

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    @Override
    public void makeBusy(Boolean busy) {
        Server server = serverRepository.findById(authUtil.loadLoggedUser().getId())
                .orElseThrow(() -> new NotFoundException("Server not found"));
        server.setIsBusy(busy);
        serverRepository.save(server);
    }
}