package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.service.ServerService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/server")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Server Controller", description = "Server API")
public class ServerController {
    private final ServerService serverService;

    @PostMapping("/create")
    @Operation(summary = "Create Server")
    public ResponseEntity<ServerDTO> create(@RequestBody @Valid ServerDTO dto) {
        ServerDTO result = serverService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Server by id")
    public ResponseEntity<ServerDTO> findById(@PathVariable("id") Integer id) {
        ServerDTO serverDTO = serverService.findById(id);
        return ResponseEntity.ok(serverDTO);
    }
    @PutMapping("/update")
    @Operation(summary = "Update Server")
    public ResponseEntity<ServerDTO> update(@RequestBody @Valid ServerDTO dto) {
        ServerDTO result = serverService.update(dto);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "Delete Server for Admins only")
    public ResponseEntity<Response<?>> delete(@RequestBody @Valid ServerDTO dto) {
        return ResponseMaker.okMessage("Server deleted");
    }
}