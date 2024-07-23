package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.server.ServerDTO;
import uz.ccrew.matchmaking.dto.server.ServerCreateDTO;
import uz.ccrew.matchmaking.dto.server.ServerUpdateDTO;

import org.springframework.data.domain.Page;

public interface ServerService {
    ServerDTO create(ServerCreateDTO dto);
    ServerDTO update(Integer id,ServerUpdateDTO dto);
    void delete(Integer id);
    ServerDTO getById(Integer id);
    Page<ServerDTO> getList(int page, int size);
    void changeBusy(Boolean busy);
}