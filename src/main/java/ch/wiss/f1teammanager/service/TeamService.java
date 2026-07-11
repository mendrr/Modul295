package ch.wiss.f1teammanager.service;

import ch.wiss.f1teammanager.dto.TeamDTO;
import ch.wiss.f1teammanager.dto.TeamFormDTO;
import ch.wiss.f1teammanager.exception.TeamNotFoundException;
import ch.wiss.f1teammanager.mapper.TeamMapper;
import ch.wiss.f1teammanager.model.Team;
import ch.wiss.f1teammanager.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Logik-Schicht für Teams. Liegt zwischen Controller und Repository
 * und gibt nach aussen nur DTOs zurück, nie das Entity.
 */
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Gibt alle Teams als DTO-Liste zurück.
     * @Transactional hält die DB-Session offen, damit der Mapper
     * die (lazy geladenen) Fahrer noch nachladen kann.
     */
    @Transactional
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamMapper::toDTO)
                .toList();
    }

    /**
     * Gibt ein einzelnes Team zurück oder wirft TeamNotFoundException.
     */
    @Transactional
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
        return TeamMapper.toDTO(team);
    }

    /**
     * Legt ein neues Team (mit seinen Fahrern) an.
     */
    public TeamDTO createTeam(TeamFormDTO form) {
        Team team = TeamMapper.toEntity(form);
        Team saved = teamRepository.save(team);
        return TeamMapper.toDTO(saved);
    }

    /**
     * Ändert Name und Standort eines bestehenden Teams.
     * Die Fahrer-Liste lässt diese einfache Version unverändert.
     */
    @Transactional
    public TeamDTO updateTeam(Long id, TeamFormDTO form) {
        Team existing = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
        existing.setName(form.name());
        existing.setBase(form.base());
        Team saved = teamRepository.save(existing);
        return TeamMapper.toDTO(saved);
    }

    /**
     * Löscht ein Team anhand seiner id.
     */
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new TeamNotFoundException(id);
        }
        teamRepository.deleteById(id);
    }
}
