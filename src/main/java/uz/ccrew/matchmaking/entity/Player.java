package uz.ccrew.matchmaking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ccrew.matchmaking.enums.Rank;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player extends Auditable {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(unique = true, nullable = false, length = 32)
    private String nickname;
    @Enumerated(EnumType.STRING)
    @Column
    private Rank rank;
    @Column(nullable = false)
    private Integer points = 0;
}