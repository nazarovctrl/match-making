package uz.ccrew.matchmaking.dto.user;

import uz.ccrew.matchmaking.enums.UserRole;

public record UserUpdateDTO(String login,
                            String password,
                            UserRole role) {
    public UserUpdateDTO withRole(UserRole role) {
        return new UserUpdateDTO(login(), password(), role);
    }
}
