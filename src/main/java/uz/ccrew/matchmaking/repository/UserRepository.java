package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.exp.NotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    default User loadById(Integer id) {
        return findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    Optional<User> findByLogin(String login);
}
