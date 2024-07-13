package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.dto.user.UserUpdateDTO;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.mapper.UserMapper;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.service.UserService;
import uz.ccrew.matchmaking.util.AuthUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO get() {
        User user = authUtil.loadLoggedUser();
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getById(Integer userId) {
        User user = userRepository.loadById(userId);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(UserUpdateDTO dto) {
        User user = authUtil.loadLoggedUser();

        dto = dto.withRole(user.getRole());

        update(user, dto);

        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateById(Integer userId, UserUpdateDTO dto) {
        User user = userRepository.loadById(userId);

        update(user, dto);

        return userMapper.toDTO(user);
    }

    private void update(User user, UserUpdateDTO dto) {
        boolean different = false;

        if (dto.password() != null) {
            String password = passwordEncoder.encode(dto.password());
            if (!user.getPassword().equals(password)) {
                user.setPassword(password);
                different = true;
            }
        }

        if (dto.login() != null && !user.getLogin().equals(dto.login())) {
            user.setLogin(dto.login());
            different = true;
        }

        if (dto.role() != null && !user.getRole().equals(dto.role())) {
            user.setRole(dto.role());
            different = true;
        }

        if (different) {
            user.setCredentialsModifiedDate(new Date());
            userRepository.save(user);
        }
    }
}
