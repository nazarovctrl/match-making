package uz.ccrew.matchmaking.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.ccrew.matchmaking.security.JwtTokenFilter;
import uz.ccrew.matchmaking.security.JwtTokenProvider;
import uz.ccrew.matchmaking.service.JwtProperties;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // он разрешает пользоваться безопасностью через методы
public class SecurityConfig {

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;


    public SecurityConfig(@Lazy JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() //Отключает CSRF (Cross-Site Request Forgery)
                .cors() // Включает поддержку Cross-Origin Resource Sharing
                .and()
                .httpBasic()
                .disable()// Отключает HTTP Basic аутентификацию
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // указывает, что не нужно создавать сессии, так как аутентификация будет выполняться с использованием токенов
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) ->{
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Unouthorized");
                })) // Настраивает обработку исключений, связанных с аутентификацией и доступом
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Unouthorized");
                }))
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll() // это для Swagger
                .anyRequest().authenticated() // Задает правила авторизации для различных URL-путей
                .and()
                .anonymous() // Отключает анонимную аутентификацию.
                .disable()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build(); // фильтр будет обрабатывать токены JWT, выполнять аутентификацию и устанавливать контекст
    }
}