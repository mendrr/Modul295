package ch.wiss.f1teammanager.service;
import ch.wiss.f1teammanager.dto.TeamDTO;
import ch.wiss.f1teammanager.mapper.TeamMapper;
import ch.wiss.f1teammanager.model.Team;
import ch.wiss.f1teammanager.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDTO> dtos = new ArrayList<>();
        for (Team team : teams) {
            dtos.add(TeamMapper.toDTO(team));
        }
        return dtos;
    }
}
