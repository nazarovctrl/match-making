package uz.ccrew.matchmaking.security;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.repository.UserRepository;
import static uz.ccrew.matchmaking.security.JWTEntityFactory.mapToGrantedAuthorities;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login).get();
        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getRole());
    }
}
