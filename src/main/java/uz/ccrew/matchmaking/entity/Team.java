package uz.ccrew.matchmaking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @UuidGenerator
    private UUID teamId;
    @Column(nullable = false)
    private Integer number;
    @ManyToOne
    @JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "teams_f1"))
    private Match match;

//    @OneToMany(cascade = CascadeType.REMOVE)
//    @JoinTable(name = "team_players",
//            joinColumns = @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "team_players_f1")),
//            inverseJoinColumns = @JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "team_players_f2")))
//    private List<Player> players;
}
