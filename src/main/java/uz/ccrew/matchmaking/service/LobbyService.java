package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.lobby.LobbyCreateDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyUpdateDTO;

public interface LobbyService {

    LobbyDTO create(LobbyCreateDTO dto);

    LobbyDTO update(LobbyUpdateDTO dto);

    LobbyDTO get();
}
