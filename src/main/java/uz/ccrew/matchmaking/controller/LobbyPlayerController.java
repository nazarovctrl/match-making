package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.lobby.LobbyPlayerDTO;
import uz.ccrew.matchmaking.service.LobbyPlayerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lobby-player")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Lobby Controller", description = "Lobby API")
public class LobbyPlayerController {
    private final LobbyPlayerService lobbyPlayerService;

    public LobbyPlayerController(LobbyPlayerService lobbyPlayerService) {
        this.lobbyPlayerService = lobbyPlayerService;
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Response<?>> leave() {
        lobbyPlayerService.leave();
        return ResponseMaker.okMessage("You left from the lobby");
    }

    @DeleteMapping("/kick/{playerId}")
    public ResponseEntity<Response<?>> kick(@PathVariable("playerId") Integer playerId) {
        lobbyPlayerService.kick(playerId);
        return ResponseMaker.okMessage("Kicked from the lobby");
    }

    @GetMapping("/players/{lobbyId}")
    public ResponseEntity<Response<List<LobbyPlayerDTO>>> getPlayers(@PathVariable("lobbyId") String lobbyId) {
        List<LobbyPlayerDTO> result = lobbyPlayerService.getPlayers(lobbyId);
        return ResponseMaker.ok(result);
    }
}
