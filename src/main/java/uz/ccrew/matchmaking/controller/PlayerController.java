package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.entity.Player;
import uz.ccrew.matchmaking.mapper.PlayerMapper;
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
    private final PlayerMapper playerMapper;

    @PostMapping("/create")
    @Operation(summary = "Create Player")
    public ResponseEntity<Response<PlayerDTO>> createPlayer(PlayerDTO playerDTO) {
        Player player = playerMapper.toEntity(playerDTO);
        Player updatedUser = playerService.createPlayer(player);
        return ResponseMaker.ok(playerMapper.toDTO(updatedUser));
    }

    @GetMapping("/{nickname}")
    @Operation(summary = "Get Player by Nickname")
    public ResponseEntity<Response<PlayerDTO>> getPlayerByNickname(@PathVariable String nickname) {
        Player player = playerService.getPlayerByNickname(nickname);
        return ResponseMaker.ok(playerMapper.toDTO(player));
    }

    @PutMapping
    @Operation(summary = "Update Player")
    public ResponseEntity<Response<PlayerDTO>> updatePlayer(PlayerDTO playerDTO) {
        Player player = playerMapper.toEntity(playerDTO);
        Player updatedUser = playerService.updatePlayer(player);
        return ResponseMaker.ok(playerMapper.toDTO(updatedUser));
    }

    @DeleteMapping("/{nickname}")
    @Operation(summary = "Delete Player by Nickname")
    public ResponseEntity<Response<Void>> deletePlayer(@PathVariable String nickname) {
        Player player = playerService.getPlayerByNickname(nickname);
        playerService.deletePlayer(player);
        return ResponseMaker.ok();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all players")
    public ResponseEntity<Response<List<PlayerDTO>>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseMaker.ok(playerMapper.toDTOList(players));
    }
}