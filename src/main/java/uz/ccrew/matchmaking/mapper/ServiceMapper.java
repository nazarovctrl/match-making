package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.entity.Server;

import org.springframework.stereotype.Component;

@Component
public class ServiceMapper implements Mapper<ServerDTO, Server> {

    @Override
    public Server toEntity(ServerDTO dto) {
        return Server.builder()
                .name(dto.name())
                .location(dto.location())
                .build();
    }

    @Override
    public ServerDTO toDTO(Server server) {
        return ServerDTO.builder()
                .name(server.getName())
                .location(server.getLocation())
                .isBusy(server.getIsBusy())
                .build();
    }
}