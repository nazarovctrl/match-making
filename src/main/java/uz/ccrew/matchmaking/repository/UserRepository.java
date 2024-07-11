package uz.ccrew.matchmaking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.ccrew.matchmaking.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}
