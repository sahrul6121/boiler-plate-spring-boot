package base.project.restapi.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessListResponse<BaseModel> {
    private String message;

    private List<BaseModel> data;

    public ResponseEntity<SuccessListResponse<BaseModel>> response(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }
}
