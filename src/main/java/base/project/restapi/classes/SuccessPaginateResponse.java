package base.project.restapi.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
public class SuccessPaginateResponse {
    String message;

    Long total;

    Integer pageSize;

    Integer totalPage;

    Integer pageNumber;

    List<?> data;

    public SuccessPaginateResponse(
        String message,
        Long total,
        Integer pageSize,
        Integer pageNumber,
        List<?> data
    ) {
        this.message = message;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.data = data;
        this.totalPage = 1;

        if (total > 0 && total > pageSize) {
            this.totalPage = Math.toIntExact(total / pageSize);
        }
    }

    public ResponseEntity<SuccessPaginateResponse> response(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }
}
