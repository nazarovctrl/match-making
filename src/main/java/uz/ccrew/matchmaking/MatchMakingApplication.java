package uz.ccrew.matchmaking;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication
@EnableAsync
public class MatchMakingApplication {
    public static void main(String[] args) {
        SpringApplication.run(MatchMakingApplication.class, args);
    }
}
