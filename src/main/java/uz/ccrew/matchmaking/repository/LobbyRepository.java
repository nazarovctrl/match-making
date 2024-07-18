package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Lobby;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LobbyRepository extends BasicRepository<Lobby, UUID> {
    @Modifying
    @Transactional
    @Query("delete Lobby w where w.id=?1")
    void deleteById(UUID uuid);
}