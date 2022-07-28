package base.project.restapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import base.project.restapi.classes.ErrorResponse;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        List<String> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(field -> field.getField() + ", " + field.getDefaultMessage()).toList();

        List<String> globalErrors = ex.getBindingResult()
            .getGlobalErrors()
            .stream()
            .map(field -> field.getObjectName() + ", " + field.getDefaultMessage()).toList();

        List<String> errors = new ArrayList<>();

        errors.addAll(globalErrors);

        errors.addAll(fieldErrors);

        ErrorResponse err = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
    "Validation Errors",
            "",
            errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            Exception ex,
            WebRequest request) {
        ErrorResponse err = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
        UserNotFoundException ex
    ) {
        ErrorResponse err = new ErrorResponse(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<Object> handleCustomException(
        CustomErrorException ex
    ) {
        ErrorResponse err = new ErrorResponse(
            ex.getStatus(),
            ex.getMessage()
        );

        return ResponseEntity.status(ex.getStatus()).body(err);
    }
}
