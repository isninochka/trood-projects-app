package isaeva.TroodProjectsApp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException() { }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
