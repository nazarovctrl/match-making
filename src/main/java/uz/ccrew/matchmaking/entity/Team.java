package uz.ccrew.matchmaking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @UuidGenerator
    private String id;

    @OneToMany
    private List<Player> players;
}
