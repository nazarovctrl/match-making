package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.Lobby;

import org.springframework.stereotype.Repository;

@Repository
public interface LobbyRepository extends BasicRepository<Lobby, String> {
}