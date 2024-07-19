package uz.ccrew.matchmaking.service;

import java.util.UUID;

public interface EloService {
    public void updateRatings(UUID winnerId, UUID loserId);
}