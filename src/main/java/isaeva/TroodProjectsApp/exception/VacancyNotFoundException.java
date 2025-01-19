package isaeva.TroodProjectsApp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class VacancyNotFoundException extends RuntimeException {

    public VacancyNotFoundException() { }

    public VacancyNotFoundException(String message) {
        super(message);
    }
}
