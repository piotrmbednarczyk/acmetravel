package com.piotrbednarczyk.acmetravel.controller.error;

import com.piotrbednarczyk.acmetravel.repository.error.FlightCreationException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(FlightCreationException.class)
    public ResponseEntity<Map> handleException(FlightCreationException e) {
        return getResponseWithErrorMessage(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler({ConversionFailedException.class, DataAccessException.class})
    public ResponseEntity<Map> handleException(NestedRuntimeException e) {
        return getResponseWithErrorMessage(e.getMostSpecificCause().getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> handleException(ConstraintViolationException e) {
        return getResponseWithErrorMessage(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map> handleException(MethodArgumentTypeMismatchException e) {
        return getResponseWithErrorMessage(e.getMostSpecificCause().getMessage() +
                ". Can not convert to " + e.getRequiredType(), BAD_REQUEST);
    }

    private ResponseEntity<Map> getResponseWithErrorMessage(String error, HttpStatus status) {
        return new ResponseEntity<>(singletonMap("error", error), status);
    }
}
