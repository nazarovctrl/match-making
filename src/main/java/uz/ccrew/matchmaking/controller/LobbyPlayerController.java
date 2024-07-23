package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.service.LobbyPlayerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/lobby-player")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Lobby PLayer Controller", description = "Lobby Player API")
public class LobbyPlayerController {
    private final LobbyPlayerService lobbyPlayerService;

    public LobbyPlayerController(LobbyPlayerService lobbyPlayerService) {
        this.lobbyPlayerService = lobbyPlayerService;
    }

    @PostMapping("/join/{lobbyId}")
    @Operation(summary = "Join to the lobby")
    public ResponseEntity<Response<?>> join(@PathVariable("lobbyId") String lobbyId) {
        lobbyPlayerService.join(lobbyId);
        return ResponseMaker.okMessage("You joined to the lobby");
    }

    @DeleteMapping("/leave")
    @Operation(summary = "Leave from the lobby")
    public ResponseEntity<Response<?>> leave() {
        lobbyPlayerService.leave();
        return ResponseMaker.okMessage("You left from the lobby");
    }

    @DeleteMapping("/kick/{playerId}")
    @Operation(summary = "Kick player from the lobby")
    public ResponseEntity<Response<?>> kick(@PathVariable("playerId") Integer playerId) {
        lobbyPlayerService.kick(playerId);
        return ResponseMaker.okMessage("Kicked from the lobby");
    }
}
