package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.entity.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserDTO, User> {
    @Override
    public User mapDTO(UserDTO dto) {
        return User.builder()
                .login(dto.login())
                .build();
    }

    @Override
    public UserDTO mapEntity(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .userRole(entity.getRole())
                .build();
    }
}
