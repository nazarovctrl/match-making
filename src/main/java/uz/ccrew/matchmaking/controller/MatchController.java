package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.match.MatchDTO;
import uz.ccrew.matchmaking.dto.match.MatchResultDTO;
import uz.ccrew.matchmaking.service.MatchService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/match")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Match Controller", description = "Match API")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/find")
    @Operation(summary = "Find match")
    public ResponseEntity<Response<MatchDTO>> find() {
        MatchDTO result = matchService.find();
        return ResponseMaker.ok(result);
    }

    @GetMapping("/get/{matchId}")
    @Operation(summary = "Get match by id")
    public ResponseEntity<Response<MatchDTO>> get(@PathVariable("matchId") String matchId) {
        MatchDTO result = matchService.get(matchId);
        return ResponseMaker.ok(result);
    }

    @PatchMapping("/calculate/result")
    @Operation(summary = "Calculate match result")
    public ResponseEntity<Response<?>> handleResult(@RequestBody MatchResultDTO dto) {
        matchService.handleResult(dto);
        return ResponseMaker.okMessage("Result successfully created");
    }
}
