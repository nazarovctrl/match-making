package uz.ccrew.matchmaking.util;

import uz.ccrew.matchmaking.exp.Unauthorized;
import uz.ccrew.matchmaking.security.user.UserDetailsImpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtil {
    private UserDetailsImpl getLoggedUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.isAuthenticated()) {
                return null;
            }
            return (UserDetailsImpl) authentication.getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<UserDetailsImpl> takeLoggedUser() {
        return Optional.ofNullable(getLoggedUser());
    }

    public UserDetailsImpl loadLoggedUser() {
        Optional<UserDetailsImpl> optional = takeLoggedUser();
        if (optional.isEmpty()) {
            throw new Unauthorized();
        }
        return optional.get();
    }
}
