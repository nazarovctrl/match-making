package uz.ccrew.matchmaking.mapper;

import org.springframework.stereotype.Component;
import uz.ccrew.matchmaking.dto.match.MatchDTO;
import uz.ccrew.matchmaking.entity.Match;

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
                .isStarted(match.getIsStarted())
                .mode(match.getMode())
                .teamType(match.getTeamType()).build();
    }
}
