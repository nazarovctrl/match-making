package uz.ccrew.matchmaking.security.user;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByLogin(login);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new UserDetailsImpl(optional.get());
    }
}
