package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Match;
import uz.ccrew.matchmaking.entity.Team;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.Rank;
import uz.ccrew.matchmaking.enums.TeamType;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MatchRepository extends BasicRepository<Match, UUID> {
    @Query(""" 
            select m from Match as m
            where m.status = 'CREATED'
            and m.rank = ?1
            and m.mode = ?2
            and m.teamType = ?3
            and ?4 > (select count(t.teamId) from Team as t where t.match.matchId = m.matchId )
            order by m.createdDate asc
            LIMIT 1
            """)
    Optional<Match> findMatchForFullTeam(Rank rank, MatchMode mode, TeamType teamType, Integer teamCount);

    @Query("""
            select t from Match as m
            join Team as t
            on t.match.matchId = m.matchId
            where m.status = 'CREATED'
            and m.rank = ?1
            and m.mode = ?2
            and m.teamType = ?3
            and  (select count(p.id) + ?4 from TeamPlayer p where p.team.teamId = t.teamId) <= ?5
            """)
    Optional<Team> findMatch(Rank rank, MatchMode matchMode, TeamType teamType, int playersCount, int maxPlayersCount);
}
