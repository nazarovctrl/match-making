package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.lobby.LobbyPlayerDTO;
import uz.ccrew.matchmaking.entity.LobbyPlayer;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.exp.BadRequestException;
import uz.ccrew.matchmaking.exp.NotFoundException;
import uz.ccrew.matchmaking.mapper.LobbyPlayerMapper;
import uz.ccrew.matchmaking.repository.LobbyPlayerRepository;
import uz.ccrew.matchmaking.repository.LobbyRepository;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.service.LobbyPlayerService;
import uz.ccrew.matchmaking.util.PlayerUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LobbyPlayerServiceImpl implements LobbyPlayerService {
    private final LobbyPlayerRepository lobbyPlayerRepository;
    private final LobbyRepository lobbyRepository;
    private final PlayerUtil playerUtil;
    private final LobbyPlayerMapper lobbyPlayerMapper;
    private final PlayerRepository playerRepository;

    @Transactional
    @Override
    public void leave() {
        Player player = playerUtil.loadPLayer();

        Optional<LobbyPlayer> optional = lobbyPlayerRepository.findByPlayer(player);
        if (optional.isEmpty()) {
            throw new NotFoundException("You are not in lobby to leave");
        }

        LobbyPlayer lobbyPlayer = optional.get();
        lobbyPlayerRepository.deleteByPlayerId(player.getPlayerId());

        int count = lobbyPlayerRepository.countById(lobbyPlayer.getId());
        if (count == 0) {
            lobbyRepository.deleteById(lobbyPlayer.getLobby().getId());
        }
    }

    @Override
    public void kick(Integer playerId) {
        Player playerToKick = playerRepository.loadById(playerId);

        Player player = playerUtil.loadPLayer();
        if (player.getPlayerId().equals(playerId)) {
            throw new BadRequestException("You can't kick your self");
        }

        Optional<LobbyPlayer> optional = lobbyPlayerRepository.findByPlayer(player);
        if (optional.isEmpty()) {
            throw new NotFoundException("You are not in lobby to kick");
        }
        LobbyPlayer lobbyPlayer = optional.get();
        if (!lobbyPlayer.getIsLeader()) {
            throw new BadRequestException("You can't kick from the lobby. Because you are not leader");
        }

        lobbyPlayerRepository.deleteByPlayerId(playerToKick.getPlayerId());
    }

    @Override
    public List<LobbyPlayerDTO> getPlayers(String lobbyId) {
        List<LobbyPlayer> list = lobbyPlayerRepository.findByLobby_Id(UUID.fromString(lobbyId));

        return lobbyPlayerMapper.toDTOList(list);
    }
}
