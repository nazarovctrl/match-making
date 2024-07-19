package uz.ccrew.matchmaking.dto.match;

import uz.ccrew.matchmaking.dto.player.PlayerDTO;

import lombok.Builder;

@Builder
public record TeamPlayerDTO(Integer number, PlayerDTO player) {
}
