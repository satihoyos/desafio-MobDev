package cl.mobdev.challege.rickandmorty.shared;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String,Object>> NoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(this.buildErrorObject(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(InternalError.class)
    public ResponseEntity<Map<String,Object>> InternalError(InternalError exception) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.buildErrorObject(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> Exception(InternalError exception) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.buildErrorObject(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }

    private Map<String,Object> buildErrorObject (HttpStatus status, String message) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", status.getReasonPhrase());
        result.put("status", status.value());
        result.put("message", message);
        result.put("timestamp", new Date().toString());
        return result;
    }
}
