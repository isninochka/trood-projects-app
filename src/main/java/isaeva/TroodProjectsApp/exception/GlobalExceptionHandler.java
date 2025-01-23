package isaeva.TroodProjectsApp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("messages", errors);
        return ResponseEntity.of(Optional.of(errorResponse));
    }


    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(VacancyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleProjectNotFoundException(VacancyNotFoundException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
