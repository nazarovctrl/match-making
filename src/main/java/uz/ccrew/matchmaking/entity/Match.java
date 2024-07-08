package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.Rank;
import uz.ccrew.matchmaking.enums.TeamType;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    private String id;
    @ManyToOne
    private Server serverId;
    @Enumerated(EnumType.STRING)
    @Column
    private Rank rank;
    @Enumerated(EnumType.STRING)
    @Column
    private MatchMode mode;
    @Enumerated(EnumType.STRING)
    @Column
    private TeamType teamType;
    @Column(nullable = false)
    private Boolean isStarted = false;
    @ManyToMany
    private List<Team> teams;
}
