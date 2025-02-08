package com.upendra.product_service.exception_handler;

import com.upendra.product_service.dto.FieldError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static  final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail responseBody = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        responseBody.setTitle("Validation Failed");
        List<FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map((fieldError -> new FieldError(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage())))
                .toList();

        responseBody.setProperty("errors", fieldErrors);
        return ResponseEntity
                .badRequest()
                .body(responseBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception ex) {
        logger.error("Internal Server Exception occurred", ex);
        return ResponseEntity.internalServerError().body(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"));
    }
}
