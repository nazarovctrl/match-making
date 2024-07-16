package uz.ccrew.matchmaking.dto.lobby;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.TeamType;

import lombok.Builder;

@Builder
public record LobbyDTO(String id,
                       TeamType teamType,
                       MatchMode matchMode) {
}
