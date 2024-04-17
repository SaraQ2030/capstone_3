package com.example.testcapstone3.Advise;
import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.ApiResponse.APIResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

@RestControllerAdvice
public class ControllerAdvise {
    // Our Exception
    @ExceptionHandler(value = APIException.class)
    public ResponseEntity ApiException(APIException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    // Server Validation Exception
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse>
    MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        return ResponseEntity.status(400).body(new APIResponse(msg));
    }
    // SQL Constraint Exception
    @ExceptionHandler(value =
            SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<APIResponse>
    SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String msg=e.getMessage();
        return ResponseEntity.status(400).body(new APIResponse(msg));
    }
    // SQL Constraint Exception
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<APIResponse>p(DataIntegrityViolationException e){
        return ResponseEntity.status(400).body(new APIResponse(e.getMessage()));
    }

    // SQL Constraint Exception
    // Method not allowed Exception
    @ExceptionHandler(value =
            HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<APIResponse>
    HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new APIResponse(msg));
    }
    // Json parse Exception
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse>
    HttpMessageNotReadableException(HttpMessageNotReadableException e){
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new APIResponse(msg));
    }
    // TypesMissMatch Exception required integer and enter string
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse>
    MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new APIResponse(msg));

    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<APIResponse>
    NullPointerException(NullPointerException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new APIResponse(msg));
    }
////////////////////////////////RuntimeException

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<APIResponse>
    RuntimeException(RuntimeException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new APIResponse(msg));
    }

    @ExceptionHandler(value = MethodNotAllowedException.class)
    public ResponseEntity<APIResponse>
    MethodNotAllowedException(MethodNotAllowedException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new APIResponse(msg));
    }
////////////////////////////////ResourceHttpRequestHandler
@ExceptionHandler(value = NoResourceFoundException.class)
public ResponseEntity<APIResponse>
NoResourceFoundException(NoResourceFoundException e) {
    String msg = e.getMessage();
    return ResponseEntity.status(400).body(new APIResponse(msg));
}
//


}
