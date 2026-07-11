package ch.wiss.f1teammanager.repository;

import ch.wiss.f1teammanager.model.Driver;
import ch.wiss.f1teammanager.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository-Test mit @DataJpaTest. Läuft gegen die echte PostgreSQL-DB
 * (replace = NONE) und rollt am Ende automatisch zurück.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void gespeichertesTeamKannGefundenWerden() {
        Team team = new Team();
        team.setName("Test Racing");
        team.setBase("Teststadt");
        Team saved = entityManager.persistFlushFind(team);

        assertThat(teamRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void teamHatSeineFahrer() {
        Team team = new Team();
        team.setName("Test Racing");
        team.setBase("Teststadt");
        Driver driver = new Driver();
        driver.setFirstName("Test");
        driver.setLastName("Fahrer");
        driver.setNumber(99);
        driver.setPoints(0);
        team.addDriver(driver);

        Team saved = entityManager.persistFlushFind(team);

        assertThat(saved.getDrivers()).hasSize(1);
    }
}
