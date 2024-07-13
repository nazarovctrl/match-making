package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Server;

import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends BasicRepository<Server, Integer> {
}
