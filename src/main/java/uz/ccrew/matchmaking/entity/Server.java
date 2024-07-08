package uz.ccrew.matchmaking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "servers")
public class Server {
    @Id
    @OneToOne
    private User id;
    @Column
    private String name;
    @Column
    private String location;
    @Column
    private Integer maxPlayers;
    @Column
    private Integer currentPlayers;
}
