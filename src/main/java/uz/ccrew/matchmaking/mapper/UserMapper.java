package uz.ccrew.matchmaking.mapper;

import org.springframework.stereotype.Component;
import uz.ccrew.matchmaking.dto.UserDTO;
import uz.ccrew.matchmaking.entity.User;

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
