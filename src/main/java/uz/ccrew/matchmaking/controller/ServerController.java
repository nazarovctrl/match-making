package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.service.ServerService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("server")
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

    @PostMapping("add")
    public ResponseEntity<ServerDTO> add(@RequestBody @Valid ServerDTO dto) {
        return ResponseEntity.ok(service.add(dto));
    }
}
