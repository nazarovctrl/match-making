package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.entity.Team;

import java.util.List;

public interface EloService {
    void updateRatings(List<Team> teams);
}
