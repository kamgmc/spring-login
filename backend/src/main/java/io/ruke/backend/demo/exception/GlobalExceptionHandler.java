package io.ruke.backend.demo.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception when the resource is not found and response with an error
     *
     * @param exception Exception threw
     * @param request   Request received
     * @return Response with descriptions
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        Error error = new Error(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle exception when the resource is not found and response with an error
     *
     * @param exception Exception threw
     * @param request   Request received
     * @return Response with descriptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception, WebRequest request) {
        Error error = new Error(new Date(), exception.getMessage(), request.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle exception when the resource is not found and response with an error
     *
     * @param exception Exception threw
     * @param request   Request received
     * @return Response with descriptions
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(DataIntegrityViolationException exception, WebRequest request) {
        Error error = new Error(new Date(), exception.getCause().getMessage(), exception.getMostSpecificCause().getLocalizedMessage());
        exception.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle exception when the validation throws an error
     *
     * @param exception Exception threw
     * @return Response with descriptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> ValidationException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        Error error = new Error(new Date(), "Validation error",
                fieldError != null ? fieldError.getDefaultMessage() : "Validation problems");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
