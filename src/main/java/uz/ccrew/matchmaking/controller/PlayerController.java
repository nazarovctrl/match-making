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
@RequestMapping("/api/v1/player")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Player Controller", description = "Player API")
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/create")
    @Operation(summary = "Create Player")
    public ResponseEntity<Response<PlayerDTO>> create(@RequestBody @Valid PlayerCreateDTO playerCreateDTO) {
        PlayerDTO result = playerService.create(playerCreateDTO);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/{nickname}")
    @Operation(summary = "Get Players by Nickname")
    public ResponseEntity<Response<Page<PlayerDTO>>> getByNicknameLike(@PathVariable("nickname") String nickname,
                                                                             @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                             @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<PlayerDTO> result = playerService.getByNicknameLike(nickname,page,size);
        return ResponseMaker.ok(result);
    }

    @PutMapping("/update")
    @Operation(summary = "Update Player")
    public ResponseEntity<Response<PlayerDTO>> update(@RequestBody @Valid PlayerUpdateDTO playerUpdateDTO) {
        PlayerDTO result = playerService.update(playerUpdateDTO);
        return ResponseMaker.ok(result);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Player")
    public ResponseEntity<Response<?>> delete() {
        playerService.delete();
        return ResponseMaker.okMessage("Player deleted");
    }

    @GetMapping("/get/list")
    @Operation(summary = "Get all players")
    public ResponseEntity<Response<Page<PlayerDTO>>> getList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                   @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<PlayerDTO> result = playerService.getList(page,size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/byId/{id}")
    @Operation(summary = "Get Player by id")
    public ResponseEntity<Response<PlayerDTO>> getById(@PathVariable("id") Integer id) {
        PlayerDTO result = playerService.getById(id);
        return ResponseMaker.ok(result);
    }
}