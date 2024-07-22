package uz.ccrew.matchmaking.entity;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "team_players")
@Check(name = "team_players_c1", constraints = "number between 1 and 4")
@NoArgsConstructor
@Getter
@Setter
public class TeamPlayer {
    @EmbeddedId
    private TeamPlayerId id;

    @Column
    private Integer number;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "team_players_f1"), nullable = false)
    private Team team;

    @OneToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "team_players_f2"), nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player player;

    public TeamPlayer(Team team, Player player) {
        this.id = new TeamPlayerId(team.getTeamId(), player.getPlayerId());
        this.team = team;
        this.player = player;
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamPlayerId implements Serializable {
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