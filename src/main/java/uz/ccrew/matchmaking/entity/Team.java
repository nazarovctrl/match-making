package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.TeamType;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team extends Auditable {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    @Column
    private TeamType type;
    @Enumerated(EnumType.STRING)
    @Column
    private MatchMode matchMode;
    @ManyToMany
    private List<Player> players;
    @ManyToOne
    private Player leader;
}
