package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.User;

import java.util.Optional;

public interface UserRepository extends BasicRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}
