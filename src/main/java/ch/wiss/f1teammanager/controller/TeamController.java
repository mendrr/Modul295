package ch.wiss.f1teammanager.controller;

import ch.wiss.f1teammanager.dto.TeamDTO;
import ch.wiss.f1teammanager.dto.TeamFormDTO;
import ch.wiss.f1teammanager.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-Controller für Teams. Nimmt die HTTP-Anfragen entgegen und
 * gibt die Arbeit an den TeamService weiter.
 */
@RestController
public class TeamController {

    private final TeamService teamService;

    // Spring reicht den TeamService automatisch herein (Dependency Injection)
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Gibt alle Teams zurück.
     */
    @GetMapping("/api/teams")
    public List<TeamDTO> getTeams() {
        return teamService.getAllTeams();
    }

    /**
     * Gibt ein einzelnes Team anhand seiner id zurück.
     */
    @GetMapping("/api/teams/{id}")
    public TeamDTO getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    /**
     * Legt ein neues Team an. Antwortet mit Status 201.
     */
    @PostMapping("/api/teams")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO createTeam(@Valid @RequestBody TeamFormDTO form) {
        return teamService.createTeam(form);
    }

    /**
     * Ändert ein bestehendes Team. Die id kommt aus der URL.
     */
    @PutMapping("/api/teams/{id}")
    public TeamDTO updateTeam(@PathVariable Long id, @Valid @RequestBody TeamFormDTO form) {
        return teamService.updateTeam(id, form);
    }

    /**
     * Löscht ein Team. Antwortet mit Status 204 (kein Inhalt).
     */
    @DeleteMapping("/api/teams/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }
}
