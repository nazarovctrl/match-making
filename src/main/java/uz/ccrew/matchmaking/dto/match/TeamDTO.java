package uz.ccrew.matchmaking.dto.match;

import lombok.Builder;

import java.util.List;

@Builder
public record TeamDTO(String teamId,
                      Integer number,
                      List<TeamPlayerDTO> players) {
}
