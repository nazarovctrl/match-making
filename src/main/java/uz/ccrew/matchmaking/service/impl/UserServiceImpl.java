package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.UserDTO;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.exp.NotFoundException;
import uz.ccrew.matchmaking.mapper.UserMapper;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.security.user.UserDetailsImpl;
import uz.ccrew.matchmaking.service.UserService;
import uz.ccrew.matchmaking.util.AuthUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final UserMapper userMapper;

    @Override
    public UserDTO get() {
        UserDetailsImpl userDetails = authUtil.loadLoggedUser();

        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.mapEntity(user);
    }

    @Override
    public UserDTO getById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.mapEntity(user);
    }
}
