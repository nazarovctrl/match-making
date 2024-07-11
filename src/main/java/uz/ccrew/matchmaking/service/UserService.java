package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.entity.User;

public interface UserService {
    User getByLogin(String login);
    User getById(Integer id);
    User create(User user);
}
