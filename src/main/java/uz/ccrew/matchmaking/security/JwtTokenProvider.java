package uz.ccrew.matchmaking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uz.ccrew.matchmaking.dto.auth.JwtResponse;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.service.JwtProperties;
import uz.ccrew.matchmaking.service.UserService;

import javax.crypto.SecretKey;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailsService  userDetailsService;
    private final UserService userService;
    private SecretKey key;


    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(
            final Integer userId,
            final String login,
            final Set<UserRole> roles
    ) {
        Claims claims = Jwts.claims()
                .subject(login)
                .add("id", userId)
                .add("roles", resolveRoles(roles))
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }


    private List<String> resolveRoles(Set<UserRole> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }


    public String createRefreshToken(final Integer userId, final String login) {
        Claims claims = Jwts.claims()
                .subject(login)
                .add("id", userId)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }


    public JwtResponse refreshUserTokens(
            final String refreshToken
    ) throws AccessDeniedException {
        JwtResponse jwtResponse = new JwtResponse();
        if (!isValid(refreshToken)) throw new AccessDeniedException("Доступ запрещен");


        Integer userId = Integer.valueOf(getId(refreshToken));
        User user = userService.getById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setLogin(user.getLogin());
        jwtResponse.setAccessToken(
                createAccessToken(userId, user.getLogin(), Collections.singleton(user.getRole()))
        );
        jwtResponse.setRefreshToken(
                createRefreshToken(userId, user.getLogin())
        );
        return jwtResponse;
    }

    public boolean isValid(
            final String token
    ) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload()
                .getExpiration()
                .after(new Date());
    }


    private String getId(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);
    }

    private String getUsernameFromToken(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public Authentication getAuthentication(String token){
        String userName = getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

}
