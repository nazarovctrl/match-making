package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.lobby.LobbyPlayerDTO;

import java.util.List;

public interface LobbyPlayerService {

    void leave();

    List<LobbyPlayerDTO> getPlayers(String lobbyId);

    void kick(Integer playerId);
}
