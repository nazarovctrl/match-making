package uz.ccrew.matchmaking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "team_players")
public class TeamPlayer {
    @EmbeddedId
    private TeamPlayerId id;

    @Column(nullable = false)
    private Integer number;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "team_players_f1"))
    private Team team;

    @OneToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "team_players_f2"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player player;


    @Embeddable
    class TeamPlayerId implements Serializable {
        private UUID teamId;
        private Integer playerId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TeamPlayer.TeamPlayerId that = (TeamPlayer.TeamPlayerId) o;
            return Objects.equals(teamId, that.teamId) && Objects.equals(playerId, that.playerId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(teamId, playerId);
        }
    }
}