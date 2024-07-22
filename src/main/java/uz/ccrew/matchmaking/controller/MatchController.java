package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.match.MatchDTO;
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
    // hamma turadgi match larni boshlash uchun kerak
    // agar team type solo bo'lmsa u holda matchni faqat leader boshlay oladi
    // match ni tugaganini ushlab oladigan va reyting hisoblaydigan api kerak faqat server uchun ishlaydi

    /* API list
    1. find match (notify when match starts) for Player(team leader)
    2. add match result and calculate rank points for Server
    3. get match list by PlayerId for Player
    4. get match result by matchId for Player
     */
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/join")
    @Operation(summary = "Join to queue")
    public ResponseEntity<Response<?>> find() {
        matchService.join();
        return ResponseMaker.okMessage("Successfully joined to queue");
    }

    @PatchMapping("/ready-to-play/{ready}")
    @Operation(summary = "Confirm to play in match")
    public ResponseEntity<Response<?>> readyToPlay(@PathVariable("ready") boolean isReady) {
        matchService.readyToPlay(isReady);
        return ResponseMaker.okMessage("Successfully confirmed");
    }

    @GetMapping("/get/{matchId}")
    @Operation(summary = "Get match by id")
    public ResponseEntity<Response<MatchDTO>> get(@PathVariable("matchId") String matchId) {
        MatchDTO result = matchService.get(matchId);
        return ResponseMaker.ok(result);
    }
}
