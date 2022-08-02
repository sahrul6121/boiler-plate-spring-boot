package base.project.restapi.exception;

import base.project.restapi.classes.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class FileExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFoundException(FileNotFoundException exc) {

        List<String> details = new ArrayList<>();

        details.add(exc.getMessage());

        return new ErrorResponse("File Not Found", details)
            .responseError(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {

        List<String> details = new ArrayList<String>();

        details.add(exc.getMessage());

        return new ErrorResponse("File Size Exceeded" ,details)
            .responseError(HttpStatus.BAD_REQUEST);
    }
}