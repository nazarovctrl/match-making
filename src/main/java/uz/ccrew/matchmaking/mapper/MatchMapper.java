package uz.ccrew.matchmaking.mapper;

import uz.ccrew.matchmaking.entity.Match;
import uz.ccrew.matchmaking.dto.match.MatchDTO;

import org.springframework.stereotype.Component;

@Component
public class MatchMapper implements Mapper<Object, MatchDTO, Match> {
    @Override
    public Match toEntity(Object o) {
        return null;
    }

    @Override
    public MatchDTO toDTO(Match match) {
        return MatchDTO.builder()
                .matchId(match.getMatchId().toString())
                .status(match.getStatus())
                .mode(match.getMode())
                .teamType(match.getTeamType()).build();
    }
}
