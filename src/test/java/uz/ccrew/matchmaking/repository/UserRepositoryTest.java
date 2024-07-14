package uz.ccrew.matchmaking.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.exp.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void load() {
        User user = User.builder().login("Azimjon").password("123123").build();
        userRepository.save(user);
        assertDoesNotThrow(() -> userRepository.loadById(user.getId()));
    }

    @Test
    void loadThrows() {
        assertThrows(NotFoundException.class, () -> userRepository.loadById(123));
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