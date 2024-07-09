package uz.ccrew.matchmaking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.service.UserService;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;


    /**
     * Загружает детали пользователя по имени пользователя.
     *
     * @param username Имя пользователя, для которого загружаются детали пользователя.
     * @return UserDetails, представляющий пользователя для аутентификации.
     * @throws UsernameNotFoundException Если пользователь с указанным именем пользователя не найден.
     * То есть
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByLogin(username);
        return JwtEntityFactory.create(user);
    }
}
