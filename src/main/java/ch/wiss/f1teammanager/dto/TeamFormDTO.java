package ch.wiss.f1teammanager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Eingabe-Objekt für das Anlegen und Ändern eines Teams.
 * Ohne id (die vergibt der Server). Die Fahrer können gleich
 * verschachtelt mitgegeben werden, wie in Block 07A das OrderFormDTO.
 */
public record TeamFormDTO(
        @NotBlank
        String name,
        @NotBlank
        String base,
        @Valid
        List<DriverFormDTO> drivers
) {}
