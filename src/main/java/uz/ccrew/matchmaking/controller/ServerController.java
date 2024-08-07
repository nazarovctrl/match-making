package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.service.ServerService;
import uz.ccrew.matchmaking.dto.server.ServerCreateDTO;
import uz.ccrew.matchmaking.dto.server.ServerUpdateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/server")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Server Controller", description = "Server API")
public class ServerController {
    private final ServerService serverService;

    @PostMapping("/create")
    @Operation(summary = "Create server")
    public ResponseEntity<ServerDTO> create(@RequestBody @Valid ServerCreateDTO dto) {
        ServerDTO result = serverService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Find server by id")
    public ResponseEntity<ServerDTO> findById(@PathVariable("id") Integer id) {
        ServerDTO serverDTO = serverService.getById(id);
        return ResponseEntity.ok(serverDTO);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update server")
    public ResponseEntity<ServerDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid ServerUpdateDTO dto) {
        ServerDTO result = serverService.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete server")
    public ResponseEntity<Response<?>> delete(@PathVariable("id") Integer id) {
        serverService.delete(id);
        return ResponseMaker.okMessage("Server deleted");
    }

    @GetMapping("/list")
    @Operation(summary = "Get all servers")
    public ResponseEntity<Response<Page<ServerDTO>>> getList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                             @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<ServerDTO> result = serverService.getList(page, size);
        return ResponseMaker.ok(result);
    }

    @PatchMapping("/change-busy/{isBusy}")
    @Operation(summary = "Make server busy or idle. For only Server role")
    public ResponseEntity<Response<?>> changeBusy(@PathVariable("isBusy") Boolean isBusy) {
        serverService.changeBusy(isBusy);
        return ResponseMaker.okMessage("The server's busy state has changed");
    }
}