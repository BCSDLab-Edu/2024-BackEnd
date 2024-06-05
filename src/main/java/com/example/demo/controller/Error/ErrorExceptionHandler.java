package com.example.demo.controller.Error;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ErrorExceptionHandler {


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> EmptyResultDataAccessException(EmptyResultDataAccessException e) {
        final ErrorResponse response = ErrorResponse.from(ErrorCode.NOT_EXIST_ELEMENT);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> NullPointerException(NullPointerException e) {
        final ErrorResponse response = ErrorResponse.from(ErrorCode.NULL_ELEMENT);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> DataIntegrityViolationException(DataIntegrityViolationException e) {
        final ErrorResponse response = ErrorResponse.from(ErrorCode.NOT_EXIST_USER);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append("[");
            stringBuilder.append(fieldError.getField());
            stringBuilder.append("](은)는 ");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(" 입력된 값: [");
            stringBuilder.append(fieldError.getRejectedValue());
            stringBuilder.append("]\n");
        }

        return stringBuilder.toString();

    }
}
