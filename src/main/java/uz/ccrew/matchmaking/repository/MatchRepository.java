package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.enums.Rank;
import uz.ccrew.matchmaking.entity.Match;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.entity.TeamPlayer;
import uz.ccrew.matchmaking.enums.MatchStatus;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MatchRepository extends BasicRepository<Match, UUID> {
    @Query(""" 
            select m
              from Match m
             where m.status = 'CREATED'
               and m.rank = ?1
               and m.mode = ?2
               and m.teamType = ?3
               and ?4 > (select count(t.teamId)
                           from Team t
                          where t.match.matchId = m.matchId)
             order by m.createdDate asc
             LIMIT 1
            """)
    Optional<Match> findMatch(Rank rank, MatchMode mode, TeamType teamType, Integer teamCount);

    @Query("""
            select tp
            from TeamPlayer tp
            join fetch tp.team t
            join fetch t.match m
            where tp.player.playerId=?1
            and m.status = ?2
            """)
    Optional<TeamPlayer> findByPlayerIdAndMatchStatus(Integer playerId, MatchStatus status);

    @Query("""
             select count(*) from TeamPlayer  tp
              where tp.team.match.matchId = ?1
                and tp.isReady = true
            """)
    int readyPlayersCount(UUID matchId);
}
