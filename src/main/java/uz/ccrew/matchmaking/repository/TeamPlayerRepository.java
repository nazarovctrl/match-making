package uz.ccrew.matchmaking.repository;

import org.springframework.stereotype.Repository;
import uz.ccrew.matchmaking.entity.TeamPlayer;

@Repository
public interface TeamPlayerRepository extends BasicRepository<TeamPlayer, TeamPlayer.TeamPlayerId> {
}
