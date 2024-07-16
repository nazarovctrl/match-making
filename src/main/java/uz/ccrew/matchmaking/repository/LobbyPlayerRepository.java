package uz.ccrew.matchmaking.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ccrew.matchmaking.entity.LobbyPlayer;

import org.springframework.stereotype.Repository;
import uz.ccrew.matchmaking.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LobbyPlayerRepository extends BasicRepository<LobbyPlayer, LobbyPlayer.LobbyPlayerId> {
    boolean existsByPlayer(Player player);

    Optional<LobbyPlayer> findByPlayer(Player player);

    int countById(LobbyPlayer.LobbyPlayerId id);

    @Modifying
    @Transactional
    @Query("delete LobbyPlayer w where w.player.playerId=?1")
    void deleteByPlayerId(Integer playerId);

    List<LobbyPlayer> findByLobby_Id(UUID lobby_id);
}
