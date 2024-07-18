package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.Rank;
import uz.ccrew.matchmaking.enums.TeamType;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Check;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
@Check(name = "matches_c1", constraints = "(is_started = true and server_id is not null) or (is_started = false and server_id is null)")
public class Match extends Auditable {
    @Id
    @UuidGenerator
    private String matchId;
    @Enumerated(EnumType.STRING)
    @Column
    private MatchMode mode;
    @Enumerated(EnumType.STRING)
    @Column
    private TeamType teamType;
    @Enumerated(EnumType.STRING)
    @Column
    private Rank rank;
    @ManyToOne
    @JoinColumn(name = "server_id", foreignKey = @ForeignKey(name = "match_teams_f1"))
    private Server server;
    @Column(nullable = false)
    private Boolean isStarted;

//    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JoinTable(name = "match_teams",
//            inverseJoinColumns = @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "match_teams_f1")),
//            joinColumns = @JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "match_teams_f2"))
//    )
//    private List<Team> teams;
}
