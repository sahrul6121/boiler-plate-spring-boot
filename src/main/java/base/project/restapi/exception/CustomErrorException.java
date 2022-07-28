package base.project.restapi.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomErrorException extends RuntimeException {
    private HttpStatus status = null;

    private Object data = null;

    public CustomErrorException() {
        super();
    }

    public CustomErrorException(
        String message
    ) {
        super(message);
    }

    public CustomErrorException(
        HttpStatus status,
        String message
    ) {
        this(message);
        this.status = status;
    }

    public CustomErrorException(
        HttpStatus status,
        String message,
        Object data
    ) {
        this(
            status,
            message
        );
        this.data = data;
    }
}
