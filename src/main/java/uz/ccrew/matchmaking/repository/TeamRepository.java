package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.enums.Rank;
import uz.ccrew.matchmaking.entity.Team;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public interface TeamRepository extends BasicRepository<Team, UUID> {

    @Query("""
            select t
              from Team as t
             where t.match.status = 'CREATED'
               and t.match.rank = ?1
               and t.match.mode = ?2
               and t.match.teamType = ?3
               and (select count(p.id) + ?4
                      from TeamPlayer p
                     where p.team.teamId = t.teamId) <= ?5
            """)
    Optional<Team> findTeam(Rank rank, MatchMode matchMode, TeamType teamType, int playersCount, int maxPlayersCount);

    Integer countByMatch_MatchId(UUID matchId);

    List<Team> findByMatch_MatchId(UUID matchId);
}
