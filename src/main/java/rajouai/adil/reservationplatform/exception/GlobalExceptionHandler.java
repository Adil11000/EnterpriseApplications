package rajouai.adil.reservationplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rajouai.adil.reservationplatform.dto.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO: Add handlers for other exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        ex.printStackTrace(); // TODO: Log the exception
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(ex.getMessage()));
    }
}
