package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Player;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Optional<Player> findByNickname(String nickname);
}