package ch.wiss.f1teammanager.exception;

/**
 * Wird geworfen, wenn kein Team mit der gesuchten id existiert.
 */
public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(Long id) {
        super("Kein Team mit der id " + id + " gefunden");
    }
}
