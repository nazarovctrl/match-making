package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.TeamType;
import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.LobbyStatus;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "lobbies")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lobby extends Auditable {
    @Id
    @UuidGenerator
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamType teamType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchMode matchMode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LobbyStatus status;

//    @OneToMany(cascade = CascadeType.REMOVE)
//    @JoinTable(name = "lobby_players",
//            joinColumns = @JoinColumn(name = "lobby_id",
//                    foreignKey = @ForeignKey(name = "lobby_players_f1", foreignKeyDefinition = "FOREIGN KEY (lobby_id) REFERENCES lobbies(playerId) ON DELETE CASCADE")),
//            inverseJoinColumns = @JoinColumn(name = "player_id",
//                    foreignKey = @ForeignKey(name = "lobby_players_f2", foreignKeyDefinition = "FOREIGN KEY (player_id) REFERENCES players(user_id) ON DELETE CASCADE")))
//    private List<Player> players;
//
//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "leader_id")
//    private Player leader;

//    public void addPlayers(Player player) {
//        if (players == null) {
//            players = new ArrayList<>();
//        }
//        players.add(player);
//    }
}
