package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Lobby;
import uz.ccrew.matchmaking.enums.LobbyStatus;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

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

    @Modifying
    @Transactional
    @Query("""
            update Lobby l
               set l.status = ?2
             where l.id in (select distinct lp.lobby.id
                              from TeamPlayer tp
                              join LobbyPlayer lp
                                on lp.player.playerId = tp.player.playerId
                             where tp.team.match.matchId = ?1)
            """)
    void updateStatusByMatchId(UUID matchId, LobbyStatus status);
}