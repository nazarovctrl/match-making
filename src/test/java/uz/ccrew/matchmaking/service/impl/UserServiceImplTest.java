package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.dto.user.UserUpdateDTO;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.exp.AlreadyExistException;
import uz.ccrew.matchmaking.repository.UserRepository;
import uz.ccrew.matchmaking.security.user.UserDetailsServiceImpl;
import uz.ccrew.matchmaking.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        User user = User.builder().login("Azimjon").password(passwordEncoder.encode("200622az")).build();
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void get() {
        UserDTO userDTO = userService.get();
        assertNotNull(userDTO);
    }

    @Test
    void getById() {
        User user = userRepository.findAll().stream().findFirst().get();
        UserDTO result = userService.getById(user.getId());
        assertNotNull(result);
    }

    @Test
    void update() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("Nazarov", "200622Az", UserRole.SERVER);

        UserDTO update = userService.update(userUpdateDTO);

        assertEquals(update.login(), userUpdateDTO.login());
        assertNotEquals(update.role(), userUpdateDTO.role()); // because PLayer can't update role
    }

    @Test
    void updateById() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("Nazarov", "200622Az", UserRole.SERVER);

        User user = userRepository.findAll().stream().findFirst().get();

        UserDTO update = userService.updateById(user.getId(), userUpdateDTO);

        assertEquals(update.login(), userUpdateDTO.login());
        assertEquals(update.role(), userUpdateDTO.role());
    }

    @Test
    void updateWithAlreadyExistsLogin() {
        User user = User.builder().login("Nazarov").password("200622az").build();
        userRepository.save(user);

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("Nazarov", "200622Az", UserRole.SERVER);
        assertThrows(AlreadyExistException.class, () -> userService.update(userUpdateDTO));
    }

    @Test
    void delete() {
        int expected = userRepository.findAll().size() - 1;

        userService.delete();

        int actual = userRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void deleteById() {
        int expected = userRepository.findAll().size() - 1;

        User user = userRepository.findAll().stream().findFirst().get();
        userService.deleteById(user.getId());

        int actual = userRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void getList() {
        List<User> list = userRepository.findAll();
        Page<UserDTO> page = userService.getList(0, 10);

        assertEquals(list.size(), page.getTotalElements());
    }
}