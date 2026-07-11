package ch.wiss.f1teammanager.repository;

import ch.wiss.f1teammanager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
//JPA.REPOSITORY: wird von spring mitgeliefert interface(standart DB methoden drin)
/**
 * Repository für Teams. Erbt von JpaRepository die fertigen DB-Methoden
 * (save, findById, findAll, count, deleteById).
 */
public interface TeamRepository extends JpaRepository<Team, Long> {

}