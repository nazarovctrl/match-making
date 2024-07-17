package uz.ccrew.matchmaking.controller;

import org.springframework.data.domain.Page;
import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.server.ServerCreateDTO;
import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.dto.server.ServerUpdateDTO;
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
    @Operation(summary = "Create server")
    public ResponseEntity<ServerDTO> create(@RequestBody @Valid ServerCreateDTO dto) {
        ServerDTO result = serverService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("byId/{id}")
    @Operation(summary = "Find server by id")
    public ResponseEntity<ServerDTO> findById(@PathVariable("id") Integer id) {
        ServerDTO serverDTO = serverService.findById(id);
        return ResponseEntity.ok(serverDTO);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update server")
    public ResponseEntity<ServerDTO> update(@RequestBody @Valid ServerUpdateDTO dto, @PathVariable("id") Integer id) {
        ServerDTO result = serverService.update(dto,id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete server")
    public ResponseEntity<Response<?>> delete(@PathVariable("id") Integer id) {
        serverService.delete(id);
        return ResponseMaker.okMessage("Server deleted");
    }

    @GetMapping("/get/list")
    @Operation(summary = "Get all servers")
    public ResponseEntity<Response<Page<ServerDTO>>> getList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                             @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<ServerDTO> result = serverService.getList(page,size);
        return ResponseMaker.ok(result);
    }
}