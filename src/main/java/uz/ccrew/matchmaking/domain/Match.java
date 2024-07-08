package uz.ccrew.matchmaking.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "matches")
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MatchMode mode;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;

    @ManyToMany
    @JoinTable(
            name = "match_player",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> players;
}