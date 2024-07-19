package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.Lobby;
import uz.ccrew.matchmaking.entity.LobbyPlayer;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.exp.AlreadyExistException;
import uz.ccrew.matchmaking.exp.BadRequestException;
import uz.ccrew.matchmaking.repository.LobbyPlayerRepository;
import uz.ccrew.matchmaking.repository.LobbyRepository;
import uz.ccrew.matchmaking.repository.PlayerRepository;
import uz.ccrew.matchmaking.service.LobbyPlayerService;
import uz.ccrew.matchmaking.util.PlayerUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LobbyPlayerServiceImpl implements LobbyPlayerService {
    private final LobbyPlayerRepository lobbyPlayerRepository;
    private final LobbyRepository lobbyRepository;
    private final PlayerUtil playerUtil;
    private final PlayerRepository playerRepository;

    @Override
    public void join(String lobbyId) {
        //TODO add check for lobby status == PREPARING
        Lobby lobby = lobbyRepository.loadById(UUID.fromString(lobbyId)); //TODO check players count

        Player player = playerUtil.loadPLayer();

        Optional<LobbyPlayer> optional = lobbyPlayerRepository.findByPlayer(player);
        if (optional.isPresent()) {
            if (optional.get().getLobby().getId().toString().equals(lobbyId)) {
                throw new AlreadyExistException("You are already joined to this lobby");
            }
            throw new AlreadyExistException("You need to leave from current lobby");
        }
        LobbyPlayer lobbyPlayer = new LobbyPlayer(lobby, player, false);
        lobbyPlayerRepository.save(lobbyPlayer);
    }

    @Transactional
    @Override
    public void leave() {
        //TODO add check for lobby status == PREPARING
        Player player = playerUtil.loadPLayer();

        LobbyPlayer lobbyPlayer = lobbyPlayerRepository.loadByPlayer(player);
        Lobby lobby = lobbyPlayer.getLobby();

        lobbyPlayerRepository.deleteByLobbyIdAndPlayerId(lobby.getId(), player.getPlayerId());

        LobbyPlayer nextLobbyLeader = lobbyPlayerRepository.findFirstByLobby_Id(lobby.getId());
        if (nextLobbyLeader == null) {
            lobbyRepository.deleteById(lobby.getId());
        } else if (lobbyPlayer.getIsLeader()) {
            nextLobbyLeader.setIsLeader(true);
            lobbyPlayerRepository.save(nextLobbyLeader);
        }
    }

    @Override
    public void kick(Integer playerId) {
        //TODO add check for lobby status == PREPARING
        Player playerToKick = playerRepository.loadById(playerId);
        Player leader = playerUtil.loadPLayer();

        LobbyPlayer lobbyLeader = lobbyPlayerRepository.loadByPlayer(leader);
        if (!lobbyLeader.getIsLeader()) {
            throw new BadRequestException("You can't kick from the lobby. Because you are not leader");
        }

        if (leader.getPlayerId().equals(playerId)) {
            throw new BadRequestException("You can't kick your self");
        }

        lobbyPlayerRepository.deleteByLobbyIdAndPlayerId(lobbyLeader.getLobby().getId(), playerToKick.getPlayerId());
    }
}
