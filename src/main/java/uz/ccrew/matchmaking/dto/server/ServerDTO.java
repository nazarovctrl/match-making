package uz.ccrew.matchmaking.dto.server;

import lombok.Builder;

@Builder
public record ServerDTO(
        Integer id,
        String name,
        String location,
        Boolean isBusy){}