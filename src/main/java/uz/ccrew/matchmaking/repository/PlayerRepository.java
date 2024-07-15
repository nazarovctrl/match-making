package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends BasicRepository<Player, Integer> {
    Page<Player> findByNicknameLike(String nickname, Pageable pageable);
}