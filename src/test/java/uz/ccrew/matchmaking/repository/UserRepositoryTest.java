package uz.ccrew.matchmaking.repository;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.exp.NotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private Integer USER_ID;

    @BeforeEach
    void setUp() {
        User user = User.builder().login("Azimjon").password("200622az").build();
        userRepository.save(user);
        USER_ID = user.getId();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void load() {
        assertDoesNotThrow(() -> userRepository.loadById(USER_ID));
    }

    @Test
    void loadThrows() {
        assertThrows(NotFoundException.class, () -> userRepository.loadById(123123123));
    }

    @Test
    void findByLogin() {
        String login = "Azimjon";
        Optional<User> optional = userRepository.findByLogin(login);
        assertTrue(optional.isPresent());

        User result = optional.get();
        assertEquals(login, result.getLogin());
    }

}