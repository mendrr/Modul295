package ch.wiss.f1teammanager.mapper;

import ch.wiss.f1teammanager.dto.DriverDTO;
import ch.wiss.f1teammanager.dto.TeamDTO;
import ch.wiss.f1teammanager.model.Driver;
import ch.wiss.f1teammanager.model.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamMapper {

    public static TeamDTO toDTO(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setBase(team.getBase());

        List<DriverDTO> driverDTOs = new ArrayList<>();
        for (Driver driver : team.getDrivers()) {
            driverDTOs.add(DriverMapper.toDTO(driver));
        }
        dto.setDrivers(driverDTOs);

        return dto;
    }
}