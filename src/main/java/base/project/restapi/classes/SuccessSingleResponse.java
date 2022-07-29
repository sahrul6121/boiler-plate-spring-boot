package base.project.restapi.classes;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class SuccessSingleResponse {
    private String message;

    private Object data;

    public ResponseEntity<SuccessSingleResponse> response(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }
}
