package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.player.PlayerCreateDTO;
import uz.ccrew.matchmaking.dto.player.PlayerDTO;
import uz.ccrew.matchmaking.dto.player.PlayerUpdateDTO;
import uz.ccrew.matchmaking.service.PlayerService;

import org.springframework.data.domain.Page;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v2/player")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Player Controller", description = "Player API")
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/create")
    @Operation(summary = "Create Player")
    public ResponseEntity<Response<PlayerDTO>> createPlayer(@Valid @RequestBody PlayerCreateDTO playerCreateDTO) {
        PlayerDTO result = playerService.createPlayer(playerCreateDTO);
        return ResponseMaker.ok(result);
    }

    @GetMapping("{nickname}")
    @Operation(summary = "Get Players by Nickname")
    public ResponseEntity<Response<Page<PlayerDTO>>> getPlayerByNicknameLike(@PathVariable String nickname,
                                                                             @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                             @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<PlayerDTO> result = playerService.getPlayersByNicknameLike(nickname,page,size);
        return ResponseMaker.ok(result);
    }

    @PutMapping("update")
    @Operation(summary = "Update Player")
    public ResponseEntity<Response<PlayerDTO>> updatePlayer(@Valid @RequestBody PlayerUpdateDTO playerUpdateDTO) {
        PlayerDTO result = playerService.updatePlayer(playerUpdateDTO);
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
    public ResponseEntity<Response<Page<PlayerDTO>>> getAllPlayers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                   @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<PlayerDTO> result = playerService.getAllPlayers(page,size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("byId/{id}")
    @Operation(summary = "Get Player by id")
    public ResponseEntity<Response<PlayerDTO>> getPlayerById(@PathVariable Integer id) {
        PlayerDTO result = playerService.getPlayerById(id);
        return ResponseMaker.ok(result);
    }
}