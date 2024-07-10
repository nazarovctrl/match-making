package uz.ccrew.matchmaking.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.repository.UserRepository;



@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getByLogin(String login){
        return userRepository.findByLogin(login).get();
    }
    @SneakyThrows
    public User getById(Integer id){
        return userRepository.findById(id).orElseThrow(()-> new Exception("User nor found"));
    }
    public User create(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()){ // есть ли такой пользователь в базе
            throw new IllegalStateException("User is already existing");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.PLAYER);
        userRepository.save(user);
        return user;
    }



}
