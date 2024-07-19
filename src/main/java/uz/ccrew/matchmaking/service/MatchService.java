package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.match.MatchDTO;

public interface MatchService {
    MatchDTO find();

    MatchDTO get(String matchId);
}
