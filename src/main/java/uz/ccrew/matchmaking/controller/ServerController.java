package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.service.ServerService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("server")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Server Controller", description = "Server API")
public class ServerController {
    //server qoshish ochirish imkoniyati faqat adminstrator uchun

    /* API list
    1. add for Administrator
    2. edit for Administrator
    3. delete for Administrator
    4. handle request to change isBusy for Server
    */
    private final ServerService service;

    public ServerController(ServerService service) {
        this.service = service;
    }

    @PostMapping("create")
    public ResponseEntity<ServerDTO> create(@RequestBody @Valid ServerDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }
}
