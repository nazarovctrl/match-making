package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2/players")
@RequiredArgsConstructor
@Tag(name = "Player Controller", description = "Player API")
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/create")
    @Operation(summary = "Create Player")
    public ResponseEntity<Response<PlayerDTO>> createPlayer(PlayerDTO playerDTO) {
        PlayerDTO result = playerService.createPlayer(playerDTO);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/{nickname}")
    @Operation(summary = "Get Player by Nickname")
    public ResponseEntity<Response<PlayerDTO>> getPlayerByNickname(@PathVariable String nickname) {
        PlayerDTO result = playerService.getPlayerByNickname(nickname);
        return ResponseMaker.ok(result);
    }

    @PutMapping("updatePlayer")
    @Operation(summary = "Update Player")
    public ResponseEntity<Response<PlayerDTO>> updatePlayer(PlayerDTO playerDTO) {
        PlayerDTO result = playerService.updatePlayer(playerDTO);
        return ResponseMaker.ok(result);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Player by Nickname")
    public ResponseEntity<Response<?>> deletePlayer() {
        playerService.deletePlayer();
        return ResponseMaker.okMessage("Player deleted");
    }

    @GetMapping("/all")
    @Operation(summary = "Get all players")
    public ResponseEntity<Response<List<PlayerDTO>>> getAllPlayers() {
        List<PlayerDTO> result = playerService.getAllPlayers();
        return ResponseMaker.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Player by id")
    public ResponseEntity<Response<PlayerDTO>> getPlayerById(@PathVariable Integer id) {
        PlayerDTO result = playerService.getPlayerById(id);
        return ResponseMaker.ok(result);
    }

}