package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.Rank;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "players")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player extends Auditable {
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Column(unique = true, nullable = false, length = 32)
    private String nickname;
    @Enumerated(EnumType.STRING)
    @Column
    @Builder.Default
    private Rank rank = Rank.BRONZE;
    @Column(nullable = false)
    @Builder.Default
    private Integer points = 0;


    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "players_f1"))
    private User user;
}