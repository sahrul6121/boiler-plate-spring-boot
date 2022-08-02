package base.project.restapi.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String message;

    private List<?> errors;

    public ErrorResponse(
        String message
    ) {
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(
        String message,
        List<?> errors
    ) {
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public ResponseEntity<Object> response(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(this);
    }

    public ResponseEntity<ErrorResponse> responseError(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(this);
    }
}