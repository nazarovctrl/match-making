package uz.ccrew.matchmaking.dto.match;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;
import uz.ccrew.matchmaking.enums.TeamType;

import lombok.Builder;

@Builder
public record MatchDTO(String matchId, MatchStatus status, MatchMode mode, TeamType teamType) {
}
