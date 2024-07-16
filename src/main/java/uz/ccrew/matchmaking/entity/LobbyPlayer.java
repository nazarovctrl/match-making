package uz.ccrew.matchmaking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "lobby_players")
public class LobbyPlayer {
    @EmbeddedId
    private LobbyPlayerId id;

    @Column(nullable = false)
    private Boolean isLeader;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("lobbyId")
    @JoinColumn(name = "lobby_id", foreignKey = @ForeignKey(name = "lobby_players_f1"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lobby lobby;

    @OneToOne(cascade = CascadeType.REMOVE)
    @MapsId("playerId")
    @JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "lobby_players_f2"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player player;


    @Embeddable
    class LobbyPlayerId implements Serializable {
        private UUID lobbyId;
        private Integer playerId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LobbyPlayerId that = (LobbyPlayerId) o;
            return Objects.equals(lobbyId, that.lobbyId) && Objects.equals(playerId, that.playerId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lobbyId, playerId);
        }
    }
}
