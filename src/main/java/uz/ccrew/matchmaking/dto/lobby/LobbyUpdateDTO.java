package uz.ccrew.matchmaking.dto.lobby;

import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;

public record LobbyUpdateDTO(TeamType teamType,
                             MatchMode matchMode) {
}
