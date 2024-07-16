package uz.ccrew.matchmaking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.lobby.LobbyDTO;
import uz.ccrew.matchmaking.service.LobbyService;

@RestController
@RequestMapping("/api/v1/lobby")
@Tag(name = "Lobby Controller",description = "Lobby API")
public class LobbyController {
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }
    // lobby ochish yokida lobby ga id si boyicha qoshilish imkoniyati player uchun

    /* API list    all for Player
      1. create lobby
      2. edit lobby
      3. join to lobby
      4. leave from lobby (when team_type != SOLO)
     */

    public ResponseEntity<Response<LobbyDTO>> create(){

    }
}
