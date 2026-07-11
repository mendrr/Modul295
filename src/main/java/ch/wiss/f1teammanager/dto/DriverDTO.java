package ch.wiss.f1teammanager.dto;

/**
 * Ausgabe-Objekt für einen Fahrer.
 * Hat bewusst KEIN team-Feld, damit beim Zurückgeben keine
 * Endlos-Rekursion entsteht (Team -> Fahrer -> Team -> ...).
 */
public record DriverDTO(
        Long id,
        String firstName,
        String lastName,
        int number,
        int points
) {}
