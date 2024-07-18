package uz.ccrew.matchmaking.service.impl;

import uz.ccrew.matchmaking.dto.match.MatchDTO;
import uz.ccrew.matchmaking.entity.*;
import uz.ccrew.matchmaking.exp.BadRequestException;
import uz.ccrew.matchmaking.mapper.MatchMapper;
import uz.ccrew.matchmaking.repository.LobbyPlayerRepository;
import uz.ccrew.matchmaking.repository.MatchRepository;
import uz.ccrew.matchmaking.repository.TeamPlayerRepository;
import uz.ccrew.matchmaking.repository.TeamRepository;
import uz.ccrew.matchmaking.service.MatchService;
import uz.ccrew.matchmaking.util.PlayerUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final PlayerUtil playerUtil;
    private final LobbyPlayerRepository lobbyPlayerRepository;
    private final MatchMapper matchMapper;
    private final TeamRepository teamRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    @Transactional
    @Override
    public MatchDTO find() {
        Player player = playerUtil.loadPLayer();
        Optional<LobbyPlayer> optionalLobbyPlayer = lobbyPlayerRepository.findByPlayer(player);
        if (optionalLobbyPlayer.isEmpty()) {
            throw new RuntimeException();
        }
        LobbyPlayer lobbyPlayer = optionalLobbyPlayer.get();
        if (!lobbyPlayer.getIsLeader()) {
            throw new BadRequestException("You are not leader");
        }
        Lobby lobby = lobbyPlayer.getLobby();

        Optional<Match> matchOptional = Optional.empty();
        List<LobbyPlayer> lobbyPlayers = lobbyPlayerRepository.findByLobby_Id(lobby.getId());
        Team team = null;

        if (lobbyPlayers.size() == lobby.getTeamType().getMaxPlayersCount()) {
            matchOptional = matchRepository.findMatchForFullTeam(player.getRank(), lobby.getMatchMode(), lobby.getTeamType(), lobby.getMatchMode().getMaxTeamCount());
        } else {
            //TODO add players to exist team
            // in this block team_type != SOLO  -> 100%
            Optional<Team> teamOptional = matchRepository.findMatch(player.getRank(), lobby.getMatchMode(), lobby.getTeamType(), lobbyPlayers.size(), lobby.getTeamType().getMaxPlayersCount());
            if (teamOptional.isPresent()) {
                team = teamOptional.get();
                matchOptional = Optional.of(team.getMatch());
            }
        }

        Match match;
        if (matchOptional.isPresent()) {
            match = matchOptional.get();
        } else {
            match = new Match(lobby.getMatchMode(), lobby.getTeamType(), player.getRank());
            matchRepository.save(match);
        }

        if (team == null) {
            team = new Team(match);
            teamRepository.save(team);
        }

        for (LobbyPlayer lp : lobbyPlayers) {
            TeamPlayer teamPlayer = new TeamPlayer(team, lp.getPlayer());
            teamPlayerRepository.save(teamPlayer);
        }

        return matchMapper.toDTO(match);
    }
}
