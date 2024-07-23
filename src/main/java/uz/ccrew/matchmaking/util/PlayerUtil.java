package uz.ccrew.matchmaking.util;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerUtil {
    private final AuthUtil authUtil;
    private final PlayerRepository playerRepository;

    public Player loadPLayer() {
        User user = authUtil.loadLoggedUser();
        return playerRepository.loadById(user.getId());
    }
}
