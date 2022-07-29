package base.project.restapi.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.MappedSuperclass;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class SuccessSingleResponse<BaseModel> {
    private String message;

    private BaseModel data;

    public ResponseEntity<SuccessSingleResponse<BaseModel>> response(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }
}
