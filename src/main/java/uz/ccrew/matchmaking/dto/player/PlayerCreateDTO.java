package uz.ccrew.matchmaking.dto.player;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import uz.ccrew.matchmaking.enums.Rank;

@Schema(name = "PlayerDTO")
@Builder
public record PlayerCreateDTO(@NotBlank(message = "Nickname must not be blank") String nickname){}