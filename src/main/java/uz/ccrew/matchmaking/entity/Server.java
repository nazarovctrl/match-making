package uz.ccrew.matchmaking.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column
    @Builder.Default
    private Boolean isBusy = false;
    @OneToOne
    @JoinColumn(name = "server_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "servers_f1"))
    private User user;
}