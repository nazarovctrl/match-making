package uz.ccrew.matchmaking.dto.player;

import uz.ccrew.matchmaking.enums.Rank;

import lombok.Builder;

@Builder
public record PlayerDTO(Integer id, String nickname, Rank rank, Integer points) {
}