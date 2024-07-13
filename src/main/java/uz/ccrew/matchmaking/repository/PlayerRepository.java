package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Player;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerRepository extends BasicRepository<Player, Integer> {
    List<Player> findByNicknameLike(String nickname);
}