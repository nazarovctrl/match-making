package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.lobby.LobbyCreateDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyDTO;
import uz.ccrew.matchmaking.entity.Lobby;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.mapper.LobbyMapper;
import uz.ccrew.matchmaking.repository.LobbyRepository;
import uz.ccrew.matchmaking.service.LobbyService;
import uz.ccrew.matchmaking.util.PlayerUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LobbyServiceImpl implements LobbyService {
    private final LobbyRepository lobbyRepository;
    private final LobbyMapper lobbyMapper;
    private final PlayerUtil playerUtil;

    @Override
    public LobbyDTO create(LobbyCreateDTO dto) {
        Player player = playerUtil.loadPLayer();



        Lobby lobby = lobbyMapper.toEntity(dto);


        lobbyRepository.save(lobby);
        return lobbyMapper.toDTO(lobby);
    }
}
