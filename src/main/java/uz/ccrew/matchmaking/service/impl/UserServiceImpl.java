package uz.ccrew.matchmaking.service.impl;

import org.springframework.data.domain.*;
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
import java.util.List;

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
        return userMapper.mapEntity(user);
    }

    @Override
    public UserDTO getById(Integer userId) {
        User user = userRepository.loadById(userId);
        return userMapper.mapEntity(user);
    }

    @Override
    public UserDTO update(UserUpdateDTO dto) {
        User user = authUtil.loadLoggedUser();

        dto = dto.withRole(user.getRole());

        update(user, dto);

        return userMapper.mapEntity(user);
    }

    @Override
    public UserDTO updateById(Integer userId, UserUpdateDTO dto) {
        User user = userRepository.loadById(userId);

        update(user, dto);

        return userMapper.mapEntity(user);
    }

    @Override
    public void delete() {
        User user = authUtil.loadLoggedUser();
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Integer userId) {
        User user = userRepository.loadById(userId);
        userRepository.delete(user);
    }

    @Override
    public Page<UserDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<User> pageObj = userRepository.findAll(pageable);

        List<User> userList = pageObj.getContent();
        List<UserDTO> dtoList = userList.stream().map(userMapper::mapEntity).toList();

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
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
        }

        if (different) {
            user.setCredentialsModifiedDate(new Date());
        }
        userRepository.save(user);
    }
}
