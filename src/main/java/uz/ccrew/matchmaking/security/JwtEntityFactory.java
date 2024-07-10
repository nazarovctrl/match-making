package uz.ccrew.matchmaking.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {


    public static JwtEntity create(final User user) {
        return new JwtEntity(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                mapToGrantedAuthorities(Collections.singletonList(user.getRole()))
        );
    }


    private static List<GrantedAuthority> mapToGrantedAuthorities(
            final List<UserRole> roles
    ) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}