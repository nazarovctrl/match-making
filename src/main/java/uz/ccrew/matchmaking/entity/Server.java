package uz.ccrew.matchmaking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "servers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Server extends Auditable {
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @OneToMany
    private List<Match> matches;
    @Column
    @Builder.Default
    private Boolean isBusy = false;
}
