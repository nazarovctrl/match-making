package uz.ccrew.matchmaking.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
// будет реализовывать паттерн factory
/**
 * Фабричный класс для создания экземпляров JwtEntity из сущностей User.
 * Реализует паттерн фабрики для инкапсуляции логики создания.
 */
public class JwtEntityFactory {

    /**
     * Создает экземпляр JwtEntity из сущности User.
     *
     * @param user Сущность пользователя, из которой создается JwtEntity.
     * @return JwtEntity, представляющий пользователя для аутентификации через JWT.
     */
    public static JwtEntity create(final User user) {
        return new JwtEntity(
                user.getId().toString(),
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