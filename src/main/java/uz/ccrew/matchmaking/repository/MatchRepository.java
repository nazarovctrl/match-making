package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.enums.Rank;
import uz.ccrew.matchmaking.entity.Match;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;

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
    Optional<Match> findMatch(Rank rank, MatchMode mode, TeamType teamType, Integer teamCount);
}
