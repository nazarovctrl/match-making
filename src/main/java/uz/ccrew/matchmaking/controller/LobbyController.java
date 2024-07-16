package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.lobby.LobbyCreateDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyDTO;
import uz.ccrew.matchmaking.service.LobbyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lobby")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Lobby Controller", description = "Lobby API")
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
      5. kick from lobby
     */

    @PostMapping("/create")
    @Operation(summary = "Create lobby")
    public ResponseEntity<Response<LobbyDTO>> create(@RequestBody @Valid LobbyCreateDTO dto) {
        LobbyDTO result = lobbyService.create(dto);
        return ResponseMaker.ok(result);
    }
}
