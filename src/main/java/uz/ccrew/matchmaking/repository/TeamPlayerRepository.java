package uz.ccrew.matchmaking.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.ccrew.matchmaking.entity.TeamPlayer;

import java.util.UUID;

@Repository
public interface TeamPlayerRepository extends BasicRepository<TeamPlayer, TeamPlayer.TeamPlayerId> {
    @Query(""" 
            select case
                     when count(w.team.teamId) > 0
                         then true
                         else false
                   end
            from TeamPlayer w
            join Team t
            on t.teamId = w.team.teamId
            join Match m
            on m.matchId = t.match.matchId
            where  m.matchId = ?1
            group by w.team.teamId
            having count(w.team.teamId) < ?2
            """)
    Boolean existNotFullTeam(UUID matchId, Integer maxPLayersCount);
}
