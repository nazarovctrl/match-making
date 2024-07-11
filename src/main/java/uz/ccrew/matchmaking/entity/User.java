package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
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
}
