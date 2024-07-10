package uz.ccrew.matchmaking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ccrew.matchmaking.entity.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Integer> {
}
