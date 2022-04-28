package by.tms.diplomrestc51.controller;

import by.tms.diplomrestc51.exception.ExistsException;
import by.tms.diplomrestc51.exception.ForbiddenException;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@PropertySource("classpath:messages.properties")
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Value("${invalidInput}")
    private String msgInvalidInput;

    @Value("${notFound}")
    private String msgNotFound;

    @Value("${exists}")
    private String msgExists;

    @Value("${forbidden}")
    private String msgForbidden;

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<Object> invalidInputException(InvalidException ex) {
        return new ResponseEntity(msgInvalidInput, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException ex) {
        return new ResponseEntity(msgNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsException.class)
    public ResponseEntity<Object> existsException(ExistsException ex) {
        return new ResponseEntity(msgExists, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> forbiddenException(ExistsException ex) {
        return new ResponseEntity(msgForbidden, HttpStatus.FORBIDDEN);
    }
}
