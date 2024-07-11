package uz.ccrew.matchmaking.dto;

import lombok.Builder;
import uz.ccrew.matchmaking.enums.UserRole;

@Builder
public record UserDTO(Integer id, String login, UserRole userRole) {

}
