package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.match.MatchDTO;
import uz.ccrew.matchmaking.dto.match.MatchResultDTO;

public interface MatchService {
    void join();

    MatchDTO get(String matchId);

    void readyToPlay(boolean isReady);

    void calculateResult(MatchResultDTO dto);
}
