package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.lobby.LobbyDTO;
import uz.ccrew.matchmaking.service.LobbyService;
import uz.ccrew.matchmaking.dto.lobby.LobbyCreateDTO;
import uz.ccrew.matchmaking.dto.lobby.LobbyUpdateDTO;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/lobby")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Lobby Controller", description = "Lobby API")
public class LobbyController {
    //TODO add API to change leader
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create lobby")
    public ResponseEntity<Response<LobbyDTO>> create(@RequestBody @Valid LobbyCreateDTO dto) {
        LobbyDTO result = lobbyService.create(dto);
        return ResponseMaker.ok(result);
    }

    @PutMapping("/update")
    @Operation(summary = "Update lobby")
    public ResponseEntity<Response<LobbyDTO>> update(@RequestBody LobbyUpdateDTO dto) {
        LobbyDTO result = lobbyService.update(dto);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/get")
    @Operation(summary = "Get lobby and all players")
    public ResponseEntity<Response<LobbyDTO>> get() {
        LobbyDTO result = lobbyService.get();
        return ResponseMaker.ok(result);
    }
}