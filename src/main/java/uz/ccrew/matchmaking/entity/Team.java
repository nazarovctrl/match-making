package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.TeamType;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column
    private TeamType type;

    @ManyToMany
    private List<Player> players;

    @ManyToOne
    private Player leader;
}
