package uz.ccrew.matchmaking.dto.match;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.TeamType;

import lombok.Builder;

@Builder
public record MatchDTO(String matchId, Boolean isStarted, MatchMode mode, TeamType teamType) {
}
