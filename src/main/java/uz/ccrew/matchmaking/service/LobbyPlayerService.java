package uz.ccrew.matchmaking.service;

public interface LobbyPlayerService {

    void join(String lobbyId);

    void leave();

    void kick(Integer playerId);
}
