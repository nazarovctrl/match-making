package uz.ccrew.matchmaking.mapper;

import org.springframework.stereotype.Component;
import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.entity.Server;

@Component
public class ServiceMapper implements Mapper<ServerDTO, Server> {


    @Override
    public Server mapDTO(ServerDTO dto) {
        return Server.builder()
                .name(dto.name())
                .location(dto.location())
                .build();
    }

    @Override
    public ServerDTO mapEntity(Server server) {
        return ServerDTO.builder()
                .name(server.getName())
                .location(server.getLocation())
                .isBusy(server.getIsBusy())
                .build();
    }
}
