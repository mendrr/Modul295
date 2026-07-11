package ch.wiss.f1teammanager;

import ch.wiss.f1teammanager.model.Driver;
import ch.wiss.f1teammanager.model.Team;
import ch.wiss.f1teammanager.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Füllt die Datenbank beim Start mit ein paar Start-Teams.
 * Läuft nur, wenn die Tabelle noch leer ist, sonst gäbe es bei
 * jedem Neustart Duplikate (idempotent).
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final TeamRepository teamRepository;

    public DataSeeder(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (teamRepository.count() == 0) {
            Team redbull = new Team();
            redbull.setName("Red Bull Racing");
            redbull.setBase("Milton Keynes");

            Driver verstappen = new Driver();
            verstappen.setFirstName("Max");
            verstappen.setLastName("Verstappen");
            verstappen.setNumber(1);
            verstappen.setPoints(0);
            redbull.addDriver(verstappen);

            Driver tsunoda = new Driver();
            tsunoda.setFirstName("Yuki");
            tsunoda.setLastName("Tsunoda");
            tsunoda.setNumber(22);
            tsunoda.setPoints(0);
            redbull.addDriver(tsunoda);

            Team ferrari = new Team();
            ferrari.setName("Scuderia Ferrari");
            ferrari.setBase("Maranello");

            Driver leclerc = new Driver();
            leclerc.setFirstName("Charles");
            leclerc.setLastName("Leclerc");
            leclerc.setNumber(16);
            leclerc.setPoints(0);
            ferrari.addDriver(leclerc);

            Driver hamilton = new Driver();
            hamilton.setFirstName("Lewis");
            hamilton.setLastName("Hamilton");
            hamilton.setNumber(44);
            hamilton.setPoints(0);
            ferrari.addDriver(hamilton);

            teamRepository.save(redbull);
            teamRepository.save(ferrari);
            System.out.println("DataSeeder: " + teamRepository.count() + " Teams in die DB geschrieben.");
        } else {
            System.out.println("DataSeeder: DB enthält schon Daten, kein Seeding nötig.");
        }
    }
}
