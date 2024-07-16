package uz.ccrew.matchmaking.service;

import uz.ccrew.matchmaking.dto.server.ServerDTO;

public interface ServerService {
    ServerDTO create(ServerDTO dto);
    ServerDTO update(ServerDTO dto);
    void delete(Integer id);
    ServerDTO findById(Integer id);
}