package dev.rafaelgalvezg.dailyprojectapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> globalExceptionHandler(Exception ex, WebRequest request) {
        CustomErrorResponse error = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        log.error("Error: ", ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> modelNotFoundExceptionHandler(ModelNotFoundException ex, WebRequest request) {
        CustomErrorResponse error = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        log.error("Error: ", ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Custom exception handler for optimistic lock exception
    @ExceptionHandler(CustomOptimisticLockException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomOptimisticLockException(CustomOptimisticLockException ex, WebRequest request) {
        CustomErrorResponse error = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        log.error("Error: ", ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);  // Código de respuesta HTTP 409
    }


    //Validations for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()
                ).collect(Collectors.joining(", "));

        CustomErrorResponse error = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false));

        log.error("Error: ", ex);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
