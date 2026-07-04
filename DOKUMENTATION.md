# 📘 Projekt-Dokumentation — F1 Team-Manager (Modul 295 LB)

> **Zweck dieser Datei:** Hier wird **jede Datei, jede Codezeile, jede Schreibweise**
> Schritt für Schritt erklärt — mit *Was macht es* und *Warum steht es so da*.
> Wenn du im Code etwas nicht verstehst, schlägst du hier nach.
>
> Diese Datei wird **laufend gepflegt**. Sie wächst mit dem Projekt mit.

---

## 🎯 Projektidee (Kurzfassung)

Ein **Backend (REST-API)**, das **Teams** und ihre **Fahrer** verwaltet.
- Ein **Team** (z.B. „Red Bull Racing") hat **mehrere Fahrer**.
- Jeder **Fahrer** gehört zu **genau einem Team**.
- Das ist eine **Eins-zu-viele-Beziehung** (`@OneToMany` / `@ManyToOne`) —
  exakt das Muster `Order`/`OrderItem` aus Aufgabe 07A, nur mit F1-Domäne.

**Technik-Stack (nur was im Kurs behandelt wurde):**
Spring Boot · JPA · PostgreSQL (Docker) · DTO + Mapper · Bean Validation ·
GlobalExceptionHandler · JUnit / Mockito. *(Kein Lombok, keine fremden Libraries.)*

---

## 🔁 Git & GitHub (LB-Pflicht: Repo + mehrere Commits)

**Repo einmalig erstellen & hochladen:** Menü **Git → GitHub → Share Project on GitHub**
→ Repository name `f1teammanager`, **Private** → **Share** → im „Initial Commit"-Fenster
alle Dateien anhaken, Commit-Message schreiben → Commit.

**Nach jedem Block committen + pushen** (zeigt, dass das Projekt gewachsen ist):
- **Git → Commit** (`Strg+K`) → Änderungen anhaken, sprechende Message (z.B. `feat: Repository`) → Commit
- **Git → Push** (`Strg+Umschalt+K`) → hochladen

Sprechende Messages helfen: `feat: Entities Team/Driver`, `feat: DB-Konfig (Docker + properties)`, …

---

## 📖 Begriffe einfach erklärt

Hier schlägst du Wörter nach, die im Code auftauchen. In einfacher Sprache.

**`JpaRepository`** — ein **fertiges Interface, das Spring mitliefert**. Darin sind alle
Standard-DB-Methoden schon eingebaut: `save()`, `findById()`, `findAll()`, `count()`,
`delete()`. Wir schreiben diese Methoden **nicht** selbst.
- *Woher?* Kam mit der Dependency `spring-boot-starter-data-jpa` (in der `pom.xml`) ins Projekt.
- *Wo?* In IntelliJ unter **External Libraries**. Mit `import
  org.springframework.data.jpa.repository.JpaRepository;` holen wir es an genau dem Ort.
  (Strg+Klick auf `JpaRepository` springt zur Original-Datei.)
- *Die „Magie":* Wir deklarieren nur das leere Interface. Beim App-Start baut Spring
  automatisch die echte Umsetzung mit allen Methoden. Wir sagen *was*, Spring liefert *wie*.
- Bild: eine Fernbedienung mit fertigen Knöpfen (speichern/suchen/löschen) — wir sagen „gib
  mir eine für `Team`", Spring baut das Gerät, das mit der DB redet.

**`extends` / Vererbung** — `extends` heisst „**erbt von**". Die erbende Sache übernimmt
automatisch alle Fähigkeiten der anderen. Bei `TeamRepository extends JpaRepository` erbt
unser Repository alle fertigen DB-Methoden (`save`, `findById`, `findAll`, `delete` …).
- Erbt eine **Klasse** von einer Klasse → die Vorlage heisst **Superklasse** (Oberklasse).
- Erbt ein **Interface** von einem Interface (unser Fall, `JpaRepository` ist ein Interface)
  → die Vorlage heisst **Super-Interface** (Ober-Interface). Prinzip identisch.
- Bild: „Elternteil" (`JpaRepository`) → „Kind" (`TeamRepository`); das Kind erbt alles.

**`application.properties`** — die **„Anmelde-Karte" der App für die Datenbank**. App und DB
sind getrennte Programme; diese Datei sagt der App *wo* die DB ist und *wie* sie sich einloggt.
Ohne sie kann die App nichts speichern.

**datasource** — die **Datenbank-Verbindung**. Die drei Zeilen `url` + `username` + `password`
zusammen = wie ein Website-Login: Adresse + Benutzer + Passwort.

**JDBC** — Javas **Standard-„Stecker"**, um mit einer Datenbank zu reden. `jdbc:postgresql://…`
heisst „dock an eine PostgreSQL-DB an dieser Adresse an".

**Hibernate** — die konkrete **Umsetzung von JPA**. Es erledigt die eigentliche Übersetzung
Java↔DB (z.B. Tabellen bauen, SQL erzeugen). Kam mit `spring-boot-starter-data-jpa` mit.

**`ddl-auto=update`** — Hibernate **baut/aktualisiert die Tabellen automatisch** aus deinen
Entities (`teams`, `drivers` entstehen von selbst). Ohne das müsstest du `CREATE TABLE`-SQL
selbst schreiben.

**Docker** — ein Werkzeug, das Programme als fertige „Päckchen" (Container) startet, ohne dass
man sie von Hand installieren muss. Wir nutzen es, um PostgreSQL zu starten. Vorteil: auf jedem
PC gleich, einfach start/stopp (wichtig für die LB-Installationsanleitung).

**PostgreSQL („postgres")** — die eigentliche **Datenbank-Software**, die unsere Daten (Teams,
Fahrer) in Tabellen speichert. Die LB verlangt genau PostgreSQL (nicht H2/in-memory).

**Container** — eine **laufende** Instanz eines Images (hier: die laufende PostgreSQL-Box). Bild:
das eingesteckte, laufende Gerät. Löscht/neustartet man den Container, ist alles **darin** weg.

**Volume** — ein **separater, dauerhafter Datenspeicher** **ausserhalb** des Containers (NICHT der
Container selbst!). Bild: ein USB-Stick am Gerät. Die DB legt ihre Daten dort ab, damit sie einen
Container-Neustart **überleben**. Ohne Volume wären Teams/Fahrer nach jedem Neustart weg.
- Wichtig: `f1-data` ist der **Speicher** (wo), **nicht** die Teams/Fahrer selbst (was). Die
  Teams/Fahrer werden von PostgreSQL *in* `f1-data` hineingeschrieben. Anfangs ist der Speicher leer.

**Docker / Container / Volume — Zusammenhang:** Docker (Werkzeug) startet einen Container
(laufende DB), der seine Daten im Volume (dauerhafter Speicher) ablegt.

**Annotation** — ein **Etikett**, das mit `@` beginnt und an Code (Klasse, Feld, Methode)
geklebt wird. Es gibt dem Code eine Zusatz-Bedeutung/Anweisung, die ein Werkzeug (JPA/Spring)
liest und ausführt. Die Annotation selbst **tut nichts** — sie ist nur ein Hinweis, erst das
Werkzeug handelt danach. Beispiele: `@Entity` („ist eine Tabelle"), `@Id` („ist der
Primärschlüssel"), `@ManyToOne` („viele gehören zu einem").
Bild: ein „Zerbrechlich"-Aufkleber auf einem Koffer — der Koffer ändert sich nicht, aber der
Gepäckträger (JPA/Spring) liest den Aufkleber und handelt entsprechend.

**JPA (Java Persistence API)** — dein **Übersetzer zwischen Java und Datenbank**.
Java denkt in *Objekten* (ein `Driver`-Objekt), die Datenbank in *Tabellen und Zeilen*.
JPA sitzt dazwischen und übersetzt automatisch in beide Richtungen. Der grosse Vorteil:
Du musst **kein SQL** von Hand schreiben — du arbeitest nur mit Java, JPA macht den
Datenbank-Teil. Die Etiketten `@Entity`, `@Id`, `@ManyToOne` sind alles Anweisungen an JPA.

**`jakarta` / `jakarta.persistence`** — die **Werkzeugkiste von JPA**. Da drin liegen
alle Annotationen (`@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne`, `@JoinColumn` …).
Die Zeile `import jakarta.persistence.*;` heisst: „hol mir **alle** Werkzeuge aus dieser
Kiste". (`persistence` = „dauerhaft speichern".)

**`@Entity`** — das Etikett, das aus einer normalen Klasse eine **Datenbank-Tabelle** macht.
- *Warum brauche ich es?* Ohne `@Entity` ist `Driver` nur eine normale Java-Klasse und wird
  **nie gespeichert** — die Datenbank weiss nichts davon. Mit `@Entity` legt JPA die Tabelle
  `drivers` an, und Fahrer bleiben **dauerhaft** gespeichert (auch nach App-Neustart).
- Für die LB Pflicht: „Daten liegen in PostgreSQL". `@Entity` ist die Brücke dorthin.
- Merksatz: **Kein `@Entity` = kein Speichern in der Datenbank.**

**`@ManyToOne` / `@JoinColumn` — die Beziehung zwischen zwei Tabellen.** Das ist der
Kern der LB (Pflicht: eine Beziehung zwischen zwei Entities).
- *Warum überhaupt?* Ohne die Beziehung wären `drivers` und `teams` zwei getrennte
  Tabellen — ein Fahrer wüsste nicht, zu welchem Team er gehört. Diese Annotationen
  stellen genau diese Verbindung her.
- *Warum `@ManyToOne`?* Es beschreibt die Anzahl: **viele** Fahrer gehören zu **einem**
  Team (z.B. Verstappen + Tsunoda → beide Red Bull). Aus Sicht des Fahrers = „viele-zu-eins".
- *Warum `@JoinColumn(name = "team_id")`?* Eine DB speichert die Verbindung über eine
  **Extra-Spalte** (Fremdschlüssel). `@JoinColumn` legt in der Tabelle `drivers` die
  Spalte `team_id` an, die auf das zugehörige Team zeigt. Jede Fahrer-Zeile weiss so,
  zu welchem Team sie gehört. Diese Seite „besitzt" die Beziehung (Owning Side).
- Exakt das Muster aus 07A: `OrderItem` hielt mit `order_id` den Fremdschlüssel zur `Order`.

**Getter / Setter** — Zugriffs-Methoden auf die Felder. Unsere Felder sind `private`
(nur die Klasse selbst darf direkt ran). Damit **andere** Teile des Programms die Werte
lesen/setzen können, gibt es Methoden:
- **Getter** = *lesen* (`getFirstName()` gibt den Vornamen zurück).
- **Setter** = *schreiben* (`setFirstName("Max")` setzt den Vornamen).
- Spring/JPA brauchen sie, um Felder in JSON zu schreiben und aus der DB zu füllen.
- Man tippt sie nicht von Hand: IntelliJ erzeugt sie mit **Alt+Einfg → Getter and Setter**.
  (Auch die Schule macht das so — in 07A steht nur `// --- Getter / Setter ---` als Platzhalter.)

---

## 🗺️ Woran wir uns halten (aus der LB-Bewertung)

Jeder dieser Punkte MUSS am Ende im Projekt sichtbar sein. Wir haken sie ab, sobald erledigt:

- [x] `@OneToMany` / `@ManyToOne`-Beziehung (Team ↔ Fahrer) ✅ **erledigt (Driver + Team)**
- [ ] 3-Schichten-Architektur: Controller → Service → Repository
- [ ] DTOs nach aussen (nie das Entity direkt), über einen Mapper
- [ ] CRUD: GET (Liste + einzeln), POST, PUT, DELETE
- [ ] Bean Validation am Form-DTO (`@NotBlank`, `@Positive`, …)
- [ ] Eigene Exception + GlobalExceptionHandler + ErrorResponse-DTO
- [ ] Daten in PostgreSQL (Docker), über JPA `@Entity`
- [ ] JavaDoc an allen Klassen (ohne getter/setter)
- [ ] 5 Tests (Repository-, Service-, Controller-Typ), alle grün
- [ ] Doku (diese Datei → wird am Ende zu PDF)
- [ ] GitHub-Repo mit mehreren Commits

---

## 🧱 Geplante Projektstruktur

Exakt wie das Quiz-Backend aus der Schule, nur mit F1-Domäne:

```
f1teammanager/
├─ pom.xml                     ← Maven-Konfig (Dependencies, Java-Version)
├─ docker-compose.yml          ← startet die PostgreSQL-Datenbank
└─ src/
   ├─ main/
   │  ├─ java/ch/wiss/f1teammanager/
   │  │  ├─ F1teammanagerApplication.java   ← Startpunkt der App
   │  │  ├─ model/        Team.java, Driver.java        (Entities + Beziehung)
   │  │  ├─ dto/          TeamDTO, DriverDTO, *FormDTO   (was nach aussen geht)
   │  │  ├─ mapper/       TeamMapper.java                (Entity → DTO)
   │  │  ├─ repository/   TeamRepository.java            (DB-Zugriff)
   │  │  ├─ service/      TeamService.java               (Logik)
   │  │  └─ controller/   TeamController.java            (die /api-Endpoints)
   │  └─ resources/
   │     └─ application.properties          ← DB-Verbindung & Einstellungen
   └─ test/java/ch/wiss/f1teammanager/       ← die 5 Tests
```

**Stack (belegt aus eurer Schul-`pom.xml`, Modul 295):** Spring Boot 4.0.6 · Java 21 ·
PostgreSQL 16. Dependencies GENAU diese vier — **nicht mehr**:
`spring-boot-starter-webmvc` · `spring-boot-starter-webmvc-test` ·
`spring-boot-starter-data-jpa` · `postgresql`.

> ⚠️ **Kein `validation`-Starter!** Eure Schule hat ihn NICHT in der pom.xml —
> `@Valid`/`@NotBlank` kommt bei euch transitiv über eine andere Dependency mit.
> Wir fügen ihn deshalb bewusst **nicht** hinzu, sonst weichen wir von der Schule ab.

---

## 📝 Schritt-für-Schritt-Protokoll

> Jeder Abschnitt hier = ein Arbeitsschritt. Neue Schritte kommen unten dazu.

### Schritt 0 — Projektordner & Doku angelegt
- **Was:** Ordner `F1-TeamManager/` erstellt, diese `DOKUMENTATION.md` angelegt.
- **Warum:** Ein fester Ort für das Projekt und eine Doku, in der ab jetzt
  jede Codezeile erklärt wird — deine Nachschlage-Referenz.

### Schritt 1 — Leeres Spring-Boot-Projekt erzeugen (direkt in IntelliJ)
- **Was:** In IntelliJ über **File → New → Project** ein leeres Projekt erzeugen.
  Kein externer Link nötig — der Ersteller ist in IntelliJ eingebaut.
- **⚠️ Stolperfalle (schon einmal passiert):** Im New-Project-Fenster **links**
  in der Liste unbedingt **„Spring Boot"** anklicken. Wählt man oben „New Project",
  bekommt man ein leeres Java-Projekt (`org.example`, nur eine `Main.java`) — falsch.
- **Quelle / erlaubt?** Block 01A sagt: für dein **eigenes Projekt** darfst du den
  bequemen Weg „über das Plugin" (= IntelliJ) nehmen. Ergebnis ist identisch.
- **Einstellungen — belegt aus eurer Schul-`pom.xml`:**

  | Feld | Wert | Warum / Beleg |
  |---|---|---|
  | Project | **Maven** | Schul-pom nutzt Maven. |
  | Language | **Java** | — |
  | Spring Boot | **4.0.6** | steht so in eurer `pom.xml` (`<version>4.0.6</version>`). |
  | Group | **ch.wiss** | identisch zur Schule (`<groupId>ch.wiss</groupId>`). |
  | Artifact | **f1teammanager** | euer Projektname (Schule: `quizbackend`). |
  | Name | **f1teammanager** | wie Artifact. |
  | Package name | **ch.wiss.f1teammanager** | Basis-Package (Schule: `ch.wiss.quizbackend`). |
  | Packaging | **Jar** | — |
  | Configuration | **Properties** | wie in Block 01A vorgegeben. |
  | Java | **21** | `<java.version>21</java.version>` in eurer pom. |
  | Dependencies | **Spring Web · Spring Data JPA · PostgreSQL Driver** | ergibt exakt eure 4 Starter (webmvc-test kommt automatisch). **Kein Validation!** |

- **Speicherort:** `...\Desktop\Claude workspace\F1-TeamManager\` →
  IntelliJ legt darin den Unterordner `f1teammanager` an.
- **Ergebnis:** ein fertiges, leeres Projekt, das sich in IntelliJ öffnet.

> 💡 Hinweis zur Reihenfolge: Eure **gespeicherte pom.xml** hat von Anfang an
> `data-jpa` + `postgresql` drin. Damit die App beim ersten Start nicht abstürzt
> (JPA ohne laufende DB = Fehler), starten wir direkt danach die Datenbank per
> Docker (nächster Schritt), genau wie in Block 01B.

✅ **Erledigt:** Projekt `f1teammanager` in IntelliJ als Spring-Boot-Projekt erstellt
(Maven, JDK 26, Java 21, Spring Boot 4.0.7, Dependencies: Spring Web, Spring Data JPA,
PostgreSQL Driver). Struktur stimmt: `F1teammanagerApplication.java` unter
`ch/wiss/f1teammanager`.

### Schritt 2 — pom.xml exakt an die Schule angleichen
Der IntelliJ-Wizard hat zwei Kleinigkeiten anders gemacht als eure Schul-`pom.xml`.
Wir gleichen sie an, damit **nichts** abweicht. Datei: `pom.xml` (im Projekt-Hauptordner).

**Änderung 1 — Spring-Boot-Version:**
- **Was:** `<version>4.0.7</version>` (im `<parent>`-Block) → auf `<version>4.0.6</version>` ändern.
- **Warum:** Eure Schule nutzt exakt 4.0.6. (4.0.7 wäre nur ein Bugfix höher und würde
  auch laufen — aber wir wollen es identisch.)

**Änderung 2 — überflüssige Test-Dependency entfernen:**
- **Was:** Diesen 5-Zeilen-Block bei den `<dependencies>` **löschen**:
  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa-test</artifactId>
      <scope>test</scope>
  </dependency>
  ```
- **Warum:** Eure Schul-`pom.xml` hat diese Dependency **nicht** (geprüft). Euer
  Repository-Test läuft mit `@SpringBootTest`, nicht mit `@DataJpaTest` — deshalb wird
  `data-jpa-test` bei euch gar nicht gebraucht. Weg damit, sonst weichen wir ab.

**Danach:** IntelliJ zeigt oben rechts ein kleines **Maven-Reload-Symbol** (kreisender
Pfeil) oder eine Leiste „Load Maven Changes". Draufklicken (oder Tastenkürzel
`Strg`+`Umschalt`+`O`), damit Maven die geänderte pom.xml neu einliest.

✅ **Erledigt:** `pom.xml` an die Schule angeglichen — Version auf **4.0.6**,
`data-jpa-test` entfernt, `spring-boot-starter-data-jpa` drin. Jetzt exakt die
4 Schul-Dependencies: `webmvc`, `webmvc-test`, `data-jpa`, `postgresql`.

> 🧠 Stolperfalle gemerkt: `spring-boot-starter-data-jpa` (die echte DB-Funktion)
> und `spring-boot-starter-data-jpa-**test**` (nur Test-Hilfe) sehen fast gleich aus.
> Wir brauchen die **ohne** `-test`.

### Schritt 3 — Erste Klasse: `Driver` (die „Viele"-Seite)
Wie in 07A bauen wir zuerst die Viele-Seite (dort `OrderItem`), weil sie den
**Fremdschlüssel** hält. Ort: neues Package `model`, Datei `Driver.java`.

```java
package ch.wiss.f1teammanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private int number;

    private int points;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Driver() {
    }

    public Driver(String firstName, String lastName, int number, int points) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.points = points;
    }

    // --- Getter / Setter ---
}
```

**Zeile für Zeile — Was & Warum:**

| Code | Was | Warum |
|---|---|---|
| `package ch.wiss.f1teammanager.model;` | sagt, in welchem Ordner/Package die Klasse liegt | Spring durchsucht ab `ch.wiss.f1teammanager` abwärts; `model` ist unser Ordner für Datenklassen. |
| `import jakarta.persistence.*;` | holt alle JPA-Werkzeuge (`@Entity`, `@Id` …) | Ohne Import kennt Java die Annotationen nicht. `*` = alle auf einmal (wie in 07A). |
| `@Entity` | macht aus der Klasse eine **DB-Tabelle** | JPA legt für jede `@Entity` eine Tabelle an. |
| `@Table(name = "drivers")` | Tabellenname = `drivers` (Mehrzahl) | Konvention wie `orders`/`order_items` in 07A. Mehrzahl, um Konflikte mit SQL-Wörtern zu vermeiden. |
| `@Id` | markiert das Primärschlüssel-Feld | Jede Tabelle braucht eine eindeutige id. |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | die **Datenbank** vergibt die id automatisch (1,2,3…) | Genau wie bei `OrderItem` in 07A — du musst nie selbst eine id setzen. |
| `private Long id;` | das id-Feld (Zahl) | `Long` (grosse Ganzzahl), Standard für DB-ids. |
| `private String firstName;` / `lastName` | Vor- und Nachname | einfache Text-Felder. |
| `private int number;` | Startnummer (z.B. 1, 44) | ganze Zahl. |
| `private int points;` | WM-Punkte | ganze Zahl. |
| `@ManyToOne` | **viele** Fahrer gehören zu **einem** Team | die „Viele"-Seite der Beziehung (wie `OrderItem` → `Order`). |
| `@JoinColumn(name = "team_id")` | erzeugt in der Tabelle `drivers` die Spalte `team_id` (Fremdschlüssel) | **Diese Seite besitzt die Beziehung** (Owning Side) — sie schreibt den FK in die DB. |
| `private Team team;` | Verweis auf das zugehörige Team-Objekt | erst nach Erstellen der Team-Klasse rot-frei (siehe Warnung unten). |
| `public Driver() { }` | leerer Konstruktor | **JPA braucht ihn zwingend**, um Objekte aus der DB zu erzeugen. |
| `public Driver(String … )` | Konstruktor mit Werten | um im Code bequem einen Fahrer anzulegen (ohne `team` — das setzen wir über das Team). |
| Getter/Setter | Zugriffsmethoden auf die Felder | Spring liest/schreibt die Felder darüber (JSON, DB). In IntelliJ mit **Alt+Einfg → Getter and Setter** automatisch erzeugen. |

> ⚠️ **Normal:** `Team` ist rot unterstrichen, weil die Klasse `Team` noch nicht existiert.
> Das beheben wir im nächsten Schritt (genau wie in 07A, wo `OrderItem` erst rot war,
> bis `Order` da war). Noch nicht starten.

> 🔧 Zwei Feinheiten, die anfangs falsch waren und angeglichen wurden:
> `private Long id;` (grosses `Long`, weil die id vor dem Speichern leer/`null` sein muss)
> und `@JoinColumn(name = "team_id")` (klein geschrieben, wie `order_id` in 07A).

### Schritt 4 — Zweite Klasse: `Team` (die „Eins"-Seite)
Das Gegenstück zu `Driver`. In 07A war das `Order`. Sobald diese Klasse existiert,
ist das Rot bei `private Team team;` in `Driver` weg. Ort: Package `model`, Datei `Team.java`.

Wir bauen sie Stück für Stück. **Stand bisher — das Grundgerüst:**
```java
package ch.wiss.f1teammanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {
}
```
| Code | Was | Warum |
|---|---|---|
| `@Entity` | macht aus `Team` eine DB-Tabelle | wie bei `Driver`. |
| `@Table(name = "teams")` | Tabellenname = `teams` (Mehrzahl) | Konvention wie `drivers`, `orders`. |

**Dann id + zwei Felder (genau wie bei `Driver`):**
```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String base;
```
| Code | Was | Warum |
|---|---|---|
| `@Id` + `@GeneratedValue(...IDENTITY)` + `private Long id;` | Primärschlüssel, DB vergibt ihn automatisch | identisch zu `Driver`. |
| `private String name;` | Teamname, z.B. „Red Bull Racing" | Text-Feld. |
| `private String base;` | Standort/Sitz, z.B. „Milton Keynes" | Text-Feld. |

**Das Herzstück — die `@OneToMany`-Liste der Fahrer:**
Dafür braucht es oben zwei zusätzliche Imports:
```java
import java.util.ArrayList;
import java.util.List;
```
Und im Körper der Klasse:
```java
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Driver> drivers = new ArrayList<>();
```
| Code | Was | Warum |
|---|---|---|
| `@OneToMany` | **ein** Team hat **viele** Fahrer | die „Eins"-Seite der Beziehung (Gegenstück zu `@ManyToOne` in `Driver`). |
| `mappedBy = "team"` | verweist auf das Feld `team` in der Klasse `Driver` | sagt: „Ich besitze die Beziehung **nicht** — sie wird drüben vom Feld `team` verwaltet (dort liegt der Fremdschlüssel `team_id`)." Verhindert eine doppelte/überflüssige Tabelle. |
| `cascade = CascadeType.ALL` | speicherst/löschst du das Team, werden seine Fahrer **automatisch mit** gespeichert/gelöscht | du musst Fahrer nicht einzeln speichern. Wie bei `Order`/`OrderItem` in 07A. |
| `orphanRemoval = true` | nimmst du einen Fahrer aus der Liste, wird er aus der DB **gelöscht** | ein „verwaister" Fahrer ohne Team wird entfernt. |
| `private List<Driver> drivers` | die Liste der zugehörigen Fahrer | ein Team kennt so alle seine Fahrer. |
| `= new ArrayList<>()` | startet als **leere** Liste | damit die Liste nie `null` ist (kein Absturz, wenn noch keine Fahrer da sind). |

> 📌 Merke: `@ManyToOne` (in `Driver`) + `@OneToMany(mappedBy=...)` (in `Team`) sind die
> **zwei Seiten derselben Beziehung**. `Driver` besitzt sie (hat den Fremdschlüssel),
> `Team` spiegelt sie nur (`mappedBy`). Exakt das Muster `OrderItem`/`Order` aus 07A.

**Hilfsmethode `addDriver(...)` — verbindet beide Seiten der Beziehung:**
```java
    public void addDriver(Driver driver) {
        drivers.add(driver);
        driver.setTeam(this);
    }
```
| Code | Was | Warum |
|---|---|---|
| `public void addDriver(Driver driver)` | Methode, die einen Fahrer zum Team hinzufügt | ein sauberer Weg, Fahrer anzuhängen. |
| `drivers.add(driver);` | legt den Fahrer in die Liste des Teams | Team-Seite der Beziehung. |
| `driver.setTeam(this);` | setzt beim Fahrer das Team auf **dieses** Team (`this`) | Fahrer-Seite — schreibt den Fremdschlüssel `team_id`. |

> 💡 **Warum beide Zeilen?** Die Beziehung ist bidirektional: das Team kennt seine Fahrer
> **und** jeder Fahrer kennt sein Team. Setzt man nur eine Seite, wird der Fremdschlüssel
> in der DB nicht korrekt geschrieben. Die Hilfsmethode macht beides in einem Schritt,
> so vergisst man keine Seite. `this` = „dieses Team-Objekt hier". Exakt wie `addItem` in 07A.

✅ **Erledigt:** `Team` fertig (Felder, `@OneToMany`, `addDriver`, Getter/Setter).
Beide Entity-Klassen stehen → die Kern-Beziehung der LB ist gebaut.

### Schritt 5 — Das Repository (`TeamRepository`)
Der **Repository**-Baustein ist die Schicht, die mit der **Datenbank** redet (das „R" in
Controller → Service → Repository). Ort: neues Package `repository`, Datei `TeamRepository.java`.
**Wichtig: das ist ein `interface`, keine `class`!**

```java
package ch.wiss.f1teammanager.repository;

import ch.wiss.f1teammanager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
```
| Code | Was | Warum |
|---|---|---|
| `interface` (nicht `class`) | ein „Vertrag" ohne eigenen Code-Körper | Spring Data baut die Umsetzung selbst; wir schreiben nichts rein. |
| `import ...model.Team;` | holt die `Team`-Klasse | liegt in einem anderen Package (`model`), darum der Import. |
| `extends JpaRepository<Team, Long>` | erbt fertige DB-Methoden | wir bekommen `save()`, `findById()`, `findAll()`, `count()`, `delete()` **geschenkt** — ohne eine Zeile zu schreiben. |
| `<Team, Long>` | 1. Typ = die Entity, 2. Typ = der Typ des Primärschlüssels | unsere `id` ist ein `Long`, darum `Long` (in der Schule war es bei `Question` ein `String`). |

> 💡 Genau wie `OrderRepository` in 07A: `public interface OrderRepository extends JpaRepository<Order, Long>{ }` — leer, mehr braucht es nicht.
> Ein eigenes Repository für `Driver` brauchen wir **nicht**: Fahrer werden immer über ihr Team gespeichert (dank `cascade`).

**Begriffe:**
- **Repository** = die Schicht/Klasse, die Daten aus der DB holt und speichert.
- **`interface`** = eine Art „Vorlage/Vertrag". Man sagt nur *was* es können soll, nicht *wie*. Spring Data JPA liefert das *Wie* automatisch.
- **`extends`** = „erbt von" — übernimmt alle Fähigkeiten von `JpaRepository`.

### Schritt 6 — Datenbank per Docker (`docker-compose.yml`)
Damit die App Daten speichern kann, braucht sie eine laufende **PostgreSQL-Datenbank**.
Statt PostgreSQL von Hand zu installieren, starten wir sie per **Docker** (genau wie Block 01B).
Ort: **Projekt-Hauptordner** (gleiche Ebene wie `pom.xml`), Datei `docker-compose.yml`.

```yaml
services:
  postgres:
    image: postgres:16
    container_name: f1-postgres
    environment:
      POSTGRES_USER: f1User
      POSTGRES_PASSWORD: f1PW
      POSTGRES_DB: f1db
    ports:
      - "5432:5432"
    volumes:
      - f1-data:/var/lib/postgresql/data

volumes:
  f1-data:
```
| Zeile | Was | Warum |
|---|---|---|
| `services:` | Liste der Programme (Container), die laufen sollen | hier nur eines: die Datenbank. |
| `postgres:` | Name unseres Dienstes | frei wählbar; wir nennen ihn `postgres`. |
| `image: postgres:16` | welche fertige Software: PostgreSQL Version 16 | Docker lädt sie automatisch herunter. Gleiche Version wie Schule. |
| `container_name: f1-postgres` | Name des laufenden Containers | damit man ihn wiedererkennt. |
| `environment:` | Einstellungen, die in die DB gegeben werden | legt Benutzer, Passwort, DB-Name fest. |
| `POSTGRES_USER: f1User` | Benutzername der DB | zum Anmelden. |
| `POSTGRES_PASSWORD: f1PW` | Passwort der DB | zum Anmelden. |
| `POSTGRES_DB: f1db` | Name der Datenbank, die angelegt wird | hier landen die Tabellen `teams`/`drivers`. |
| `ports: - "5432:5432"` | macht die DB auf Port 5432 erreichbar | `5432` ist der Standard-Port von PostgreSQL. Links = dein PC, rechts = im Container. |
| `volumes: - f1-data:/var/lib/postgresql/data` | speichert die Daten dauerhaft | so bleiben die Daten auch nach einem Neustart erhalten. |
| `volumes: f1-data:` (unten) | meldet diesen Speicher an | gehört zur Zeile darüber. |

> ⚠️ **YAML ist einrückungs-empfindlich!** Nur **Leerzeichen**, keine Tabs, und genau die
> Einrückung wie oben (je 2 Leerzeichen tiefer). Ein falsches Leerzeichen = Fehler.
>
> 🔑 **Wichtig:** `POSTGRES_USER`, `POSTGRES_PASSWORD`, `POSTGRES_DB` hier müssen **exakt**
> mit der `application.properties` (nächster Schritt) übereinstimmen — sonst kommt die App
> nicht in die DB.

### Schritt 7 — Die App mit der DB verbinden (`application.properties`)
Diese Datei sagt der App, **wo** die Datenbank ist und **wie** sie sich anmeldet.
Ort: `src/main/resources/application.properties` (existiert schon, hat aktuell nur eine Zeile).

```properties
spring.application.name=f1teammanager
spring.datasource.url=jdbc:postgresql://localhost:5432/f1db
spring.datasource.username=f1User
spring.datasource.password=f1PW

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
| Zeile | Was | Warum |
|---|---|---|
| `spring.application.name=f1teammanager` | Name der App | war schon da. |
| `spring.datasource.url=jdbc:postgresql://localhost:5432/f1db` | Adresse der DB: auf diesem PC (`localhost`), Port `5432`, Datenbank `f1db` | so findet die App die DB aus `docker-compose.yml`. |
| `spring.datasource.username=f1User` | DB-Benutzer | muss = `POSTGRES_USER` in docker-compose sein. |
| `spring.datasource.password=f1PW` | DB-Passwort | muss = `POSTGRES_PASSWORD` sein. |
| `spring.jpa.hibernate.ddl-auto=update` | JPA legt/aktualisiert die Tabellen automatisch aus den Entities | du musst kein SQL für `CREATE TABLE` schreiben — `teams`/`drivers` entstehen von selbst. |
| `spring.jpa.show-sql=true` | zeigt das erzeugte SQL in der Konsole | zum Lernen/Nachvollziehen. |
| `spring.jpa.properties.hibernate.format_sql=true` | formatiert dieses SQL schön lesbar | reine Lesbarkeit. |

> 🔑 Die drei Werte `f1db`, `f1User`, `f1PW` müssen **exakt** mit `docker-compose.yml`
> übereinstimmen — sonst kommt die App nicht in die DB. Genau diese Einstellungen hatte
> auch euer Quiz-Backend (Block 3), nur mit `quizdb`/`quizUser`/`quizPW`.

*(nächster Schritt: Docker Desktop starten + DB hochfahren, dann App zum ersten Mal starten)*
