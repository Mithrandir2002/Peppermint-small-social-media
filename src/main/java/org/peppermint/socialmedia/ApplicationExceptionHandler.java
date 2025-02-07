package org.peppermint.socialmedia;

import org.peppermint.socialmedia.exception.EntityNotFoundException;
import org.peppermint.socialmedia.exception.ErrorResponse;
import org.peppermint.socialmedia.exception.UserExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<Object> handleUserExistedException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
