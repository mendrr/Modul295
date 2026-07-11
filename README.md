# F1 Team-Manager

Backend (REST-API) für das Modul 295. Die Anwendung verwaltet **Teams** und ihre **Fahrer**.
Ein Team hat mehrere Fahrer, jeder Fahrer gehört zu genau einem Team
(Eins-zu-viele-Beziehung, `@OneToMany` / `@ManyToOne`).

## Technik

- Spring Boot 4.0.6, Java 21
- Spring Data JPA (Hibernate)
- PostgreSQL 16, gestartet über Docker
- DTO + Mapper, Bean Validation, GlobalExceptionHandler
- JUnit für die Tests

Kein Lombok, keine zusätzlichen Bibliotheken.

## Projektstruktur

```
src/main/java/ch/wiss/f1teammanager/
  F1teammanagerApplication.java   Startpunkt der App
  DataSeeder.java                 legt beim Start Beispiel-Teams an
  model/        Team, Driver          (Entities + Beziehung)
  dto/          TeamDTO, DriverDTO, TeamFormDTO, DriverFormDTO, ErrorResponse
  mapper/       TeamMapper            (Entity <-> DTO)
  repository/   TeamRepository        (DB-Zugriff)
  service/      TeamService           (Logik)
  controller/   TeamController        (die /api-Endpoints)
  exception/    TeamNotFoundException, GlobalExceptionHandler
```

## Datenbank starten und App ausführen

1. Docker Desktop starten.
2. Im Projektordner die Datenbank hochfahren:
   ```
   docker compose up -d
   ```
   Das startet einen PostgreSQL-Container (Datenbank `f1db`, Benutzer `f1User`, Passwort `f1PW`)
   auf Port 5432.
3. Die App starten, entweder in IntelliJ über Run, oder auf der Kommandozeile:
   ```
   mvnw.cmd spring-boot:run
   ```
4. Die App läuft dann auf `http://localhost:8080`. Beim ersten Start füllt der DataSeeder
   die leere Datenbank mit zwei Beispiel-Teams (Red Bull Racing, Scuderia Ferrari).

Die Tabellen `teams` und `drivers` werden von Hibernate automatisch erzeugt
(`spring.jpa.hibernate.ddl-auto=update`).

## API-Endpoints

| Methode | Pfad | Beschreibung |

| GET | `/api/teams` | alle Teams |
| GET | `/api/teams/{id}` | ein einzelnes Team |
| POST | `/api/teams` | neues Team anlegen |
| PUT | `/api/teams/{id}` | Team ändern |
| DELETE | `/api/teams/{id}` | Team löschen |

### Beispiel: neues Team anlegen (POST /api/teams)

Request:
```json
{
  "name": "Red Bull Racing",
  "base": "Milton Keynes",
  "drivers": [
    { "firstName": "Max", "lastName": "Verstappen", "number": 1, "points": 0 }
  ]
}
```

Response (Status 201):
```json
{
  "id": 1,
  "name": "Red Bull Racing",
  "base": "Milton Keynes",
  "drivers": [
    { "id": 1, "firstName": "Max", "lastName": "Verstappen", "number": 1, "points": 0 }
  ]
}
```

Nach aussen gehen immer DTOs, nie die Entity direkt. Das `DriverDTO` hat kein `team`-Feld,
damit beim Zurückgeben keine Endlos-Rekursion entsteht.

