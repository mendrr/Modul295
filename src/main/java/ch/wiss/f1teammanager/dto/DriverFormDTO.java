package ch.wiss.f1teammanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Eingabe-Objekt für einen Fahrer beim Anlegen eines Teams.
 * Ohne id, die vergibt die Datenbank.
 */
public record DriverFormDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Positive
        int number,
        int points
) {}
