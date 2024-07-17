package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.LobbyPlayer;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.exp.NotFoundException;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LobbyPlayerRepository extends BasicRepository<LobbyPlayer, LobbyPlayer.LobbyPlayerId> {
    boolean existsByPlayer(Player player);

    Optional<LobbyPlayer> findByPlayer(Player player);

    @Modifying
    @Transactional
    @Query("delete LobbyPlayer w where w.lobby.id=?1 and w.player.playerId=?2")
    void deleteByLobbyIdAndPlayerId(UUID lobbyId, Integer playerId);

    List<LobbyPlayer> findByLobby_Id(UUID lobbyId);

    LobbyPlayer findFirstByLobby_Id(UUID lobbyId);

    default LobbyPlayer loadByPlayer(Player player) {
        Optional<LobbyPlayer> optional = findByPlayer(player);
        if (optional.isEmpty()) {
            throw new NotFoundException("You are not in lobby");
        }
        return optional.get();
    }
}
