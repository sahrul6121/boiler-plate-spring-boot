package base.project.restapi.classes;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@AllArgsConstructor
public class SuccessListResponse {
    private String message;

    private List<?> data;

    public ResponseEntity<SuccessListResponse> response(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }
}
