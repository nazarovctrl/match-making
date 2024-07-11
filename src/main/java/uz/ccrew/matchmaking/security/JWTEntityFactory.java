package uz.ccrew.matchmaking.security;

import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JWTEntityFactory {
    public static UserDetailsImpl create(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getRole());
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRole> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}