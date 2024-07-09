package com.example.demo.exception;

import com.example.demo.controller.dto.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ExceptHandler {
    @ExceptionHandler(ExceptionGenerator.class)
    private ResponseEntity<ExceptionResponse> exceptionHandling(ExceptionGenerator e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(new ExceptionResponse(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ExceptionResponse> validExceptionHandling() {
        ExceptionGenerator e = new ExceptionGenerator(StatusEnum.CREATE_OR_EDIT_EMPTY_REQUEST);
        return ResponseEntity.status(e.getStatusCode()).body(new ExceptionResponse(e));
    }


}
