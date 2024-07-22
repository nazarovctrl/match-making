package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.match.MatchDTO;
import uz.ccrew.matchmaking.dto.match.MatchResultDTO;

public interface MatchService {
    MatchDTO find();

    MatchDTO get(String matchId);

    void handleResult(MatchResultDTO dto);
}
