package uz.ccrew.matchmaking.entity;

import jakarta.persistence.*;

import java.util.List;

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
    @OneToMany
    private List<Match> matches;
    @Column
    private Boolean isBusy;
}
