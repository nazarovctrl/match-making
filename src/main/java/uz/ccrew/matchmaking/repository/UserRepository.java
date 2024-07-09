package uz.ccrew.matchmaking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ccrew.matchmaking.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}
