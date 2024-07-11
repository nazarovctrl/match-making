package uz.ccrew.matchmaking.entity;

import lombok.NoArgsConstructor;
import uz.ccrew.matchmaking.enums.UserRole;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
