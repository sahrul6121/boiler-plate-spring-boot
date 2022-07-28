package base.project.restapi.transformers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Setter
@Getter
@AllArgsConstructor
public class FileTransformModel {
    private String fileName;

    private String message;
}
