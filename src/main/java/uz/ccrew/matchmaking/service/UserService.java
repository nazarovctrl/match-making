package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.dto.user.UserUpdateDTO;

public interface UserService {
    UserDTO get();

    UserDTO getById(Integer userId);

    UserDTO update(UserUpdateDTO dto);

    UserDTO updateById(Integer userId, UserUpdateDTO dto);
}
