package uz.ccrew.matchmaking.entity;

import uz.ccrew.matchmaking.enums.MatchMode;
import uz.ccrew.matchmaking.enums.TeamType;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "lobbies")
public class Lobby extends Auditable {
    @Id
    @UuidGenerator
    private String id;
    @Enumerated(EnumType.STRING)
    @Column
    private TeamType teamType;
    @Enumerated(EnumType.STRING)
    @Column
    private MatchMode matchMode;
    @OneToMany
    private List<Player> players;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Player leader;
}
