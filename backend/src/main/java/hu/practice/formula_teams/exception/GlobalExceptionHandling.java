package hu.practice.formula_teams.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice

public class GlobalExceptionHandling {
    @ExceptionHandler({DuplicateException.class})
    public ResponseEntity<ErrorResponse> duplicateException(DuplicateException e) {
        return ResponseEntity.status(400)
                             .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> usernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(400)
                             .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException e) {
        return ResponseEntity.status(400)
                             .body(new ErrorResponse("Authentication failed - invalid username or password", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(400)
                             .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList());
        String errorMessage = "Json validation failure: " +  String.join(", ", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST));
    }
}
