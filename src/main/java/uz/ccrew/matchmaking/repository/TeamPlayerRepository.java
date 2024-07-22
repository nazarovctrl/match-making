package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.entity.Team;
import uz.ccrew.matchmaking.entity.TeamPlayer;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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
             where w.team.match.matchId = ?1
             group by w.team.teamId
            having count(w.team.teamId) < ?2
            """)
    Boolean existNotFullTeam(UUID matchId, Integer maxPLayersCount);

    List<TeamPlayer> findByTeam(Team team);

    @Query("""
             select tp.player
               from TeamPlayer tp
              where tp.team.teamId =?1
            """)
    List<String> findByMatchId(UUID matchid);
}
