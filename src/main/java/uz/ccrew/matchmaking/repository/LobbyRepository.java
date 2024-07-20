package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Lobby;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.UUID;

@Repository
public interface LobbyRepository extends BasicRepository<Lobby, UUID> {
    @Modifying
    @Transactional
    @Query(""" 
            delete Lobby w
             where w.id = ?1
            """)
    void deleteById(UUID uuid);

    @Query("""
            select distinct lp.lobby
              from TeamPlayer tp
              join LobbyPlayer lp
                on lp.player = tp.player
             where tp.team.match.matchId = ?1
            """)
    List<Lobby> findByMatchId(UUID matchId);
}