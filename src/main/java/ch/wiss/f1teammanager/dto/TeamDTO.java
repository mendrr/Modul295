package ch.wiss.f1teammanager.dto;

import java.util.List;

/**
 * Ausgabe-Objekt für ein Team.
 * Enthält die Fahrer als Liste von DriverDTO (verschachtelt),
 * so wie das OrderDTO in Block 07A seine Positionen enthält.
 */
public record TeamDTO(
        Long id,
        String name,
        String base,
        List<DriverDTO> drivers
) {}
