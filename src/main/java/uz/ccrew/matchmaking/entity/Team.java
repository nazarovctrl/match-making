package uz.ccrew.matchmaking.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "teams")
@Check(name = "teams_c1", constraints = "placement is null or placement between 1 and 100")
@Check(name = "teams_c2", constraints = "number between 1 and 100")
@NoArgsConstructor
@Getter
@Setter
public class Team {
    @Id
    @UuidGenerator
    private UUID teamId;
    @Column
    private Integer number;
    @ManyToOne
    @JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "teams_f1"), nullable = false)
    private Match match;
    @Column
    private Integer placement;

    public Team(Match match) {
        this.match = match;
    }
//    @OneToMany(cascade = CascadeType.REMOVE)
//    @JoinTable(name = "team_players",
//            joinColumns = @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "team_players_f1")),
//            inverseJoinColumns = @JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "team_players_f2")))
//    private List<Player> players;
}
