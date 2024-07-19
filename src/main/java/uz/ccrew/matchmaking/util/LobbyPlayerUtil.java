package uz.ccrew.matchmaking.util;

import uz.ccrew.matchmaking.entity.LobbyPlayer;
import uz.ccrew.matchmaking.exp.BadRequestException;
import uz.ccrew.matchmaking.repository.LobbyPlayerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LobbyPlayerUtil {
    private final LobbyPlayerRepository lobbyPlayerRepository;
    private final PlayerUtil playerUtil;

    public LobbyPlayer loadLobbyPlayer() {
        return lobbyPlayerRepository.loadByPlayer(playerUtil.loadPLayer());
    }

    public void checkToLeader(LobbyPlayer lobbyPlayer) {
        if (!lobbyPlayer.getIsLeader()) {
            throw new BadRequestException("You are not leader");
        }
    }
}
