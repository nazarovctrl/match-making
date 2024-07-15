package uz.ccrew.matchmaking.dto.player;

import uz.ccrew.matchmaking.enums.Rank;

import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PlayerDTO")
@Builder
public record PlayerDTO(String nickname,Rank rank,Integer points){}