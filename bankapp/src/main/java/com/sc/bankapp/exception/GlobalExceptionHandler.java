package com.sc.bankapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<ErrorResponse> resourceNotFound(
            ResourceNotFoundException ex,
            WebRequest request
    ){
        String path  = request.getDescription(false).replace("uri=" ,"");

        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                path
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //
    public ResponseEntity<ErrorResponse> globalExceptions(
            Exception ex,
            WebRequest request
    ){
        String path  = request.getDescription(false).replace("uri=" ,"");

        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Internal Server Error",
                ex.getMessage(),
                path
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


