package uz.ccrew.matchmaking.config;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.util.AuthUtil;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {
    private final AuthUtil authUtil;

    public AuditorAwareImpl(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }

    @Override
    public Optional<Integer> getCurrentAuditor() {
        try {
            Optional<User> optional = authUtil.takeLoggedUser();
            return optional.map(User::getId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}