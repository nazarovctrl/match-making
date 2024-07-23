package uz.ccrew.matchmaking.dto.match;

import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;

import lombok.Builder;

import java.util.List;

@Builder
public record MatchDTO(String matchId,
                       MatchStatus status,
                       MatchMode mode,
                       TeamType teamType,
                       List<TeamDTO> teams) {
}
