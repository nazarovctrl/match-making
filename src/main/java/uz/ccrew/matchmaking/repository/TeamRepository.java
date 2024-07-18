package uz.ccrew.matchmaking.repository;

import org.springframework.web.bind.annotation.RestController;
import uz.ccrew.matchmaking.entity.Team;

import java.util.UUID;

@RestController
public interface TeamRepository extends BasicRepository<Team, UUID> {
}
