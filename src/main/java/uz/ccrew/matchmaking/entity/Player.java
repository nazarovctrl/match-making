package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.Rank;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player extends Auditable {
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    @Column(unique = true, nullable = false, length = 32)
    private String nickname;
    @Enumerated(EnumType.STRING)
    @Column
    private Rank rank;
    @Column(nullable = false)
    private Integer points = 0;
}
