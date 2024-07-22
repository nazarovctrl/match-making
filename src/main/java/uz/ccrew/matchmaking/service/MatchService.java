package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.match.MatchDTO;

public interface MatchService {
    void join();

    MatchDTO get(String matchId);

    void readyToPlay(boolean isReady);
}
