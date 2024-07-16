package uz.ccrew.matchmaking.dto.lobby;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.TeamType;

public record LobbyDTO(String id, TeamType teamType, MatchMode matchMode,List<Player>) {
}
