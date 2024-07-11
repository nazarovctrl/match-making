package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new IllegalStateException("User is already existing");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.PLAYER);
        userRepository.save(user);
        return user;
    }
}
