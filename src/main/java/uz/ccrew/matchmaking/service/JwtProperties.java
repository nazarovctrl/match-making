package uz.ccrew.matchmaking.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "security.jwt") // по этому префиксу мы будем получать данные через app.yaml
public class JwtProperties {
    private String secret;
    private long access; // время access токена
    private long refresh; // время refresh токена
}
