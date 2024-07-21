package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.Rank;
import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.MatchStatus;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@Getter
@Setter
public class Match extends Auditable {
    @Id
    @UuidGenerator
    private UUID matchId;
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
    @JoinColumn(name = "server_id", foreignKey = @ForeignKey(name = "match_teams_f1"), nullable = false)
    private Server server;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status;

    public Match(MatchMode mode, TeamType teamType, Rank rank, Server server) {
        this.mode = mode;
        this.teamType = teamType;
        this.rank = rank;
        this.status = MatchStatus.CREATED;
        this.server = server;
    }
//    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JoinTable(name = "match_teams",
//            inverseJoinColumns = @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "match_teams_f1")),
//            joinColumns = @JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "match_teams_f2"))
//    )
//    private List<Team> teams;
}
