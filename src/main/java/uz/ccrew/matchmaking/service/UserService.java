package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.UserDTO;

public interface UserService {
    UserDTO get();

    UserDTO getById(Integer userId);
}
