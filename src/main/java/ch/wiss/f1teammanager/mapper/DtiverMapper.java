package ch.wiss.f1teammanager.mapper;

import ch.wiss.f1teammanager.dto.DriverDTO;
import ch.wiss.f1teammanager.model.Driver;

public class DriverMapper {

    public static DriverDTO toDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setId(driver.getId());
        dto.setFirstName(driver.getFirstName());
        dto.setLastName(driver.getLastName());
        dto.setNumber(driver.getNumber());
        dto.setPoints(driver.getPoints());
        return dto;
    }
}