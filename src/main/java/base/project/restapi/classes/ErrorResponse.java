package base.project.restapi.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    // customizing timestamp serialization format
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private String stackTrace;

    private Object data;

    private List<?> errors;

    public ErrorResponse() {
        timestamp = new Date();
    }

    public ErrorResponse(
        HttpStatus httpStatus,
        String message
    ) {
        this();

        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ErrorResponse(
        HttpStatus httpStatus,
        String message,
        String stackTrace
    ) {
        this(
            httpStatus,
            message
        );

        this.stackTrace = stackTrace;
    }

    public ErrorResponse(
        HttpStatus httpStatus,
        String message,
        String stackTrace,
        Object data
    ) {
        this(
            httpStatus,
            message,
            stackTrace
        );

        this.data = data;
    }

    public ErrorResponse(
        HttpStatus httpStatus,
        String message,
        String stackTrace,
        List<?> errors
    ) {
        this(
            httpStatus,
            message,
            stackTrace
        );

        this.errors = errors;
    }
}