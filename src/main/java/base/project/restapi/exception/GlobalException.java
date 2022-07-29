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

        return new ErrorResponse("Validation Errors", errors).response(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            Exception ex,
            WebRequest request) {
        return new ErrorResponse(ex.getMessage()).responseError(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
        UserNotFoundException ex
    ) {
        return new ErrorResponse(ex.getMessage()).responseError(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(
        CustomErrorException ex
    ) {
        return new ErrorResponse(ex.getMessage()).responseError(ex.getStatus());
    }
}
