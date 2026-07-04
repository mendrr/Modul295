package ch.wiss.f1teammanager.model;
import jakarta.persistence.*;

@Entity    //macht eine datenbanktabelle
@Table(name = "drivers")
public class Driver {

    @Id     //markiert das feld als primarykey
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int number;
    private int points;

    @ManyToOne
    @JoinColumn(name = "team_id") //erzeugt in der Tabelle drivers eine spalte team_id
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
