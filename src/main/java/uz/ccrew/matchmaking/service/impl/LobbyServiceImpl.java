package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.lobby.LobbyCreateDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyUpdateDTO;
import uz.ccrew.matchmaking.entity.Lobby;
import uz.ccrew.matchmaking.entity.LobbyPlayer;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.exp.AlreadyExistException;
import uz.ccrew.matchmaking.exp.BadRequestException;
import uz.ccrew.matchmaking.mapper.LobbyMapper;
import uz.ccrew.matchmaking.repository.LobbyPlayerRepository;
import uz.ccrew.matchmaking.repository.LobbyRepository;
import uz.ccrew.matchmaking.service.LobbyService;
import uz.ccrew.matchmaking.util.PlayerUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LobbyServiceImpl implements LobbyService {
    private final LobbyRepository lobbyRepository;
    private final LobbyPlayerRepository lobbyPlayerRepository;
    private final LobbyMapper lobbyMapper;
    private final PlayerUtil playerUtil;

    @Transactional
    @Override
    public LobbyDTO create(LobbyCreateDTO dto) {
        Player player = playerUtil.loadPLayer();

        //check player to member of another lobby
        if (lobbyPlayerRepository.existsByPlayer(player)) {
            throw new AlreadyExistException("You need to leave from current lobby");
        }

        Lobby lobby = lobbyMapper.toEntity(dto);
        lobbyRepository.save(lobby);

        //add player to the lobby as a leader
        LobbyPlayer lobbyPlayer = new LobbyPlayer(lobby, player, true);
        lobbyPlayerRepository.save(lobbyPlayer);

        return lobbyMapper.toDTO(lobby);
    }

    @Override
    public LobbyDTO update(LobbyUpdateDTO dto) {
        Player player = playerUtil.loadPLayer();
        Optional<LobbyPlayer> optional = lobbyPlayerRepository.findByPlayer(player);
        if (optional.isEmpty()) {
            throw new BadRequestException("You are not in lobby");
        }
        LobbyPlayer lobbyPlayer = optional.get();
        if (!lobbyPlayer.getIsLeader()) {
            throw new BadRequestException("You can't update lobby");
        }

        Lobby lobby = lobbyPlayer.getLobby();
        lobby.setTeamType(dto.teamType()); //TODO if team type changes to small team check lobby-players count
        lobby.setMatchMode(dto.matchMode());

        lobbyRepository.save(lobby);
        return lobbyMapper.toDTO(lobby);
    }
}
