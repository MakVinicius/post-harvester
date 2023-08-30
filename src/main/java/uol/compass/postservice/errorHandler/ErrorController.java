package uol.compass.postservice.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.postservice.dto.ErrorDTO;

import java.util.List;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handlingResponseStatusException(ResponseStatusException exception) {
        ErrorDTO response = new ErrorDTO(List.of(exception.getReason()));

        return new ResponseEntity<>(response, exception.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handlingMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        ErrorDTO response = new ErrorDTO(List.of("You need to input an integer value for postId"));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
