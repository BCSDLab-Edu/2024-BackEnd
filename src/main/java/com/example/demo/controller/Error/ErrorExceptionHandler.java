package com.example.demo.controller.Error;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
    public ResponseEntity<?> EmptyResultDataAccessException(EmptyResultDataAccessException e){
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_EXIST_ELEMENT);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> NullPointerException(NullPointerException e){
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NullPointerException);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    //@ExceptionHandler()


}
