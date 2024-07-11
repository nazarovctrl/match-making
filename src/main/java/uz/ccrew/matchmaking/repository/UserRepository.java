package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}
