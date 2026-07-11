package ch.wiss.f1teammanager.mapper;

import ch.wiss.f1teammanager.dto.DriverDTO;
import ch.wiss.f1teammanager.dto.DriverFormDTO;
import ch.wiss.f1teammanager.dto.TeamDTO;
import ch.wiss.f1teammanager.dto.TeamFormDTO;
import ch.wiss.f1teammanager.model.Driver;
import ch.wiss.f1teammanager.model.Team;

import java.util.List;

/**
 * Wandelt zwischen Team/Driver-Entities und ihren DTOs um.
 * Reine Funktionen ohne Zustand, darum static und ein privater
 * Konstruktor, damit man die Klasse nicht aus Versehen instanziert.
 */
public class TeamMapper {

    private TeamMapper() {
    }

    /**
     * Baut aus einem Team-Entity ein TeamDTO (inkl. der Fahrer).
     */
    public static TeamDTO toDTO(Team team) {
        List<DriverDTO> driverDTOs = team.getDrivers().stream()
                .map(TeamMapper::toDriverDTO)
                .toList();
        return new TeamDTO(team.getId(), team.getName(), team.getBase(), driverDTOs);
    }

    /**
     * Baut aus einem Driver-Entity ein DriverDTO (ohne team-Feld).
     */
    public static DriverDTO toDriverDTO(Driver driver) {
        return new DriverDTO(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getNumber(),
                driver.getPoints()
        );
    }

    /**
     * Baut aus dem Formular ein neues Team-Entity.
     * Die Fahrer werden mit addDriver angehängt, damit auch die
     * Rückverbindung (driver.team) gesetzt ist.
     */
    public static Team toEntity(TeamFormDTO form) {
        Team team = new Team();
        team.setName(form.name());
        team.setBase(form.base());

        if (form.drivers() != null) {
            for (DriverFormDTO driverForm : form.drivers()) {
                Driver driver = new Driver();
                driver.setFirstName(driverForm.firstName());
                driver.setLastName(driverForm.lastName());
                driver.setNumber(driverForm.number());
                driver.setPoints(driverForm.points());
                team.addDriver(driver);
            }
        }
        return team;
    }
}
