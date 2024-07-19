package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.entity.Team;
import uz.ccrew.matchmaking.dto.match.TeamDTO;

import org.springframework.stereotype.Component;

@Component
public class TeamMapper implements Mapper<Object, TeamDTO, Team> {
    @Override
    public Team toEntity(Object o) {
        return null;
    }

    @Override
    public TeamDTO toDTO(Team team) {
        return TeamDTO.builder()
                .teamId(team.getTeamId().toString())
                .number(team.getNumber()).build();
    }
}
