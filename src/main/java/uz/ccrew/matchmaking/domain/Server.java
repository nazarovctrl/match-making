package uz.ccrew.matchmaking.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private int maxPlayers;
    private int currentPlayers;

    @OneToMany(mappedBy = "server")
    private List<Match> matches;
}
