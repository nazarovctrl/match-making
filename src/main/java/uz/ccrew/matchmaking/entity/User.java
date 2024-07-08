package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.UserRole;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
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
}
