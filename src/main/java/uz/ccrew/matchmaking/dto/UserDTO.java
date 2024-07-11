package uz.ccrew.matchmaking.dto;

import uz.ccrew.matchmaking.enums.UserRole;

import lombok.Builder;

@Builder
public record UserDTO(Integer id, String login, UserRole userRole) {

}
