package ch.wiss.f1teammanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Einheitliches Format für Fehler-Antworten der API.
 * fieldErrors wird nur bei Validierungsfehlern befüllt, sonst
 * per @JsonInclude weggelassen.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        Map<String, String> fieldErrors
) {}
