package uz.ccrew.matchmaking.dto.lobby;

import uz.ccrew.matchmaking.dto.player.PlayerDTO;

import lombok.Builder;
@Builder
public record LobbyPlayerDTO(PlayerDTO player, boolean isLeader) {
}
