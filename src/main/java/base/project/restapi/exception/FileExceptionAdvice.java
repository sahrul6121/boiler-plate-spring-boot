package base.project.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import base.project.restapi.transformers.model.ResponseErrorTransformModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class FileExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException exc) {

        List<String> details = new ArrayList<>();

        details.add(exc.getMessage());

        ResponseErrorTransformModel err = new ResponseErrorTransformModel(
            LocalDateTime.now(), "File Not Found" , details
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {

        List<String> details = new ArrayList<String>();

        details.add(exc.getMessage());

        ResponseErrorTransformModel err = new ResponseErrorTransformModel(
            LocalDateTime.now(), "File Size Exceeded" ,details
        );

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(err);
    }
}