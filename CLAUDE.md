# CLAUDE.md — Arbeitsanweisung für dieses Projekt

> Diese Datei wird von Claude Code automatisch gelesen. Sie sorgt dafür, dass Claude auf
> **jedem Gerät** (PC oder Laptop) gleich arbeitet. Der begleitende Chat ist lokal und reist
> NICHT mit — diese Datei + `DOKUMENTATION.md` sind die Übergabe.

## Projekt
**F1 Team-Manager** — Backend (REST-API) für das Modul 295 LB (Weiterführung von Modul 294).
Domäne: **Team → Fahrer** (`@OneToMany` / `@ManyToOne`), Vorlage ist das `Order`/`OrderItem`-
Muster aus Kurs-Aufgabe **07A**. Der User ist **Anfänger** und lernt das gerade.

## So MUSST du (Claude) mit dem User arbeiten — strikt einhalten
1. **Der User schreibt ALLEN Java-Code selbst.** Du fasst Code NICHT an. Ausnahme: reine
   Konfig (pom.xml, docker-compose.yml, application.properties), und nur wenn er es delegiert.
2. **Nur EIN winziger Schritt pro Nachricht**, dann STOPP und auf „fertig" warten. **Niemals**
   ganze Klassen/Code-Blöcke auf einmal geben. Zeile für Zeile, jede vorher erklärt.
3. **Nur Schulstoff.** Kein „Best Practice", kein „so macht man das normalerweise", keine
   unbesprochenen Libraries/Muster. Im Zweifel in den Kursunterlagen nachschauen — **nicht raten**.
4. **Du pflegst `DOKUMENTATION.md`.** JEDE Codezeile, jede Funktion, jeder Begriff wird dort
   **sofort und automatisch** erklärt (Was + Warum), OHNE dass der User danach fragt.
5. **Ruhig und knapp** antworten, auf Deutsch. Der User wird wütend bei Belehrungen, Dumps
   oder wenn Claude seinen eigenen Plan durchdrückt. **Er hat die Kontrolle.**

## Technischer Stack (exakt wie die Schule — NICHT abweichen)
- Spring Boot **4.0.6**, Java **21** (JDK 26 nur als Werkzeug, `java.version=21`).
- Dependencies NUR: `spring-boot-starter-webmvc`, `spring-boot-starter-webmvc-test`,
  `spring-boot-starter-data-jpa`, `postgresql`. **KEIN `validation`-Starter** (kommt transitiv).
- Datenbank: **PostgreSQL 16 via Docker** (`docker-compose.yml`), DB `f1db` / User `f1User` / PW `f1PW`.
- Kein Lombok.

## Kursunterlagen (die Vorlage — exakt spiegeln)
Liegen im OneDrive unter `Module Wiss/Modul 295` (Blöcke 1–7 + fertiges Referenzprojekt
`quizbackend` mit Lösungscode je Block). Beim Bauen immer den Schul-Stil dieser Dateien treffen,
z.B. `quizbackend` als Referenz für Repository/Service/Controller/DTO/Mapper/Exception/Tests.

## Aktueller Stand & nächste Schritte
Details + Schritt-für-Schritt-Protokoll stehen in **`DOKUMENTATION.md`**. Kurzfassung:
- ✅ Entities `Team` + `Driver` mit `@OneToMany`/`@ManyToOne` (+ `addDriver`, Getter/Setter)
- ✅ `TeamRepository` (interface, `extends JpaRepository<Team, Long>`)
- ✅ `docker-compose.yml` + `application.properties` (DB-Konfig)
- ⏭️ **Nächstes:** Docker Desktop starten → DB hochfahren → App zum ERSTEN Mal starten.
- ⏭️ Danach (wie in 07A/Kurs): DTOs + Mapper, Service, Controller, eigene Exception +
  GlobalExceptionHandler, Seed-Daten, dann die 5 Tests. Siehe LB-Checkliste in `DOKUMENTATION.md`.

## Git
Branch **`Modul-295`**, Remote `origin` = `github.com/mendrr/Modul295`. Nach jedem Block
committen + pushen. Bei Gerätewechsel: vorher pushen, am anderen Gerät zuerst `git pull`.
