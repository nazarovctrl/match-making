package uz.ccrew.matchmaking.security;

import uz.ccrew.matchmaking.enums.UserRole;
import uz.ccrew.matchmaking.security.jwt.JWTAuthenticationFilter;
import uz.ccrew.matchmaking.security.user.UserAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthenticationFilter authenticationFilter;
    private final UserAuthenticationEntryPoint authenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(true);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler.authenticationEntryPoint(authenticationEntryPoint))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth.requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers("/api/v1/auth/register", "api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/refresh").hasAnyAuthority(UserRole.all())
                        .requestMatchers("api/v1/user/*").hasAnyAuthority(UserRole.all())
                        .requestMatchers("api/v1/user/**").hasAuthority(UserRole.ADMINISTRATOR.name())
                        .requestMatchers("/api/v1/player/**").hasAuthority(UserRole.PLAYER.name())
                        .requestMatchers("/api/v1/server/change-busy/*").hasAuthority(UserRole.SERVER.name())
                        .requestMatchers("/api/v1/server/list","/api/v1/server/get/","/api/v1/server/update/"
                                ,"/api/v1/server/create","/api/v1/server/delete/").hasAuthority(UserRole.ADMINISTRATOR.name())
                        .requestMatchers("/api/v1/player/**", "/api/v1/lobby/**", "/api/v1/lobby-player/**").hasAuthority(UserRole.PLAYER.name())
                        .anyRequest().authenticated());
        return httpSecurity.build();
    }
}