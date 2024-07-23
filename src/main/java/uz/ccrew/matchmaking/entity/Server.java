package uz.ccrew.matchmaking.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "servers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Server extends Auditable {
    @Id
    @Column(name = "server_id")
    private Integer serverId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Boolean isBusy;


    @OneToOne
    @JoinColumn(name = "server_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "servers_f1"), nullable = false)
    private User user;
}