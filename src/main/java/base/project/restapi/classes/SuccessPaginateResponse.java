package base.project.restapi.classes;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@AllArgsConstructor
public class SuccessPaginateResponse {
    private String message;

    private Long total;

    private Integer per_page;

    private Integer total_page;

    private Integer current_page;

    private List<?> data;

    public ResponseEntity<SuccessPaginateResponse> response(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }
}
