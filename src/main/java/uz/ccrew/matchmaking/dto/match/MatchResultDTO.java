package uz.ccrew.matchmaking.dto.match;

import java.util.List;
public record MatchResultDTO(String matchId, List<TeamResult> teamResults) {
}
