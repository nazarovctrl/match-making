package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.TeamType;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column
    private TeamType type;
    @Enumerated(EnumType.STRING)
    @Column
    private MatchMode matchMode;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "team_players",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Player> players;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Player leader;
    @Column(columnDefinition = "bool default true")
    private Boolean isActive = true;
}
