package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.dto.server.ServerCreateDTO;
import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.entity.Server;

import org.springframework.stereotype.Component;

@Component
public class ServerMapper implements Mapper<ServerCreateDTO, ServerDTO, Server> {
    public Server toEntity(ServerCreateDTO dto) {
        return Server.builder()
                .name(dto.name())
                .location(dto.location())
                .build();
    }

    @Override
    public ServerDTO toDTO(Server server) {
        return ServerDTO.builder()
                .id(server.getServerId())
                .name(server.getName())
                .location(server.getLocation())
                .isBusy(server.getIsBusy())
                .build();
    }
}