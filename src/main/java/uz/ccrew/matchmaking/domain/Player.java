package uz.ccrew.matchmaking.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private int rating;

    @ManyToMany(mappedBy = "players")
    private List<Match> matches;
}
