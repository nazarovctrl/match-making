package uz.ccrew.matchmaking.repository;

import org.springframework.data.jpa.repository.Query;
import uz.ccrew.matchmaking.entity.Player;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.ccrew.matchmaking.entity.User;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, User> {

    Optional<Player> findByNickname(String nickname);

}