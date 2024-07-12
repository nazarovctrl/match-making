package uz.ccrew.matchmaking.config;

import uz.ccrew.matchmaking.security.user.UserDetailsImpl;
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
            Optional<UserDetailsImpl> optional = authUtil.takeLoggedUser();
            return optional.map(UserDetailsImpl::getId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}