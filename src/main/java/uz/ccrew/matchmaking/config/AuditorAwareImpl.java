package uz.ccrew.matchmaking.config;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.repository.UserRepository;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {
    private final UserRepository userRepository;

    public AuditorAwareImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Integer> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.isAuthenticated()) {
                return Optional.empty();
            }
            UserDetails user = (UserDetails) authentication.getPrincipal();
            Optional<User> byLogin = userRepository.findByLogin(user.getUsername());
            return byLogin.map(User::getId).or(Optional::empty);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}