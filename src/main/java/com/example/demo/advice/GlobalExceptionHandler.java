package com.example.demo.advice;

import com.example.demo.exception.HTTPApiException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return new ResponseEntity<>("해당 데이터를 찾을 수 없음.", HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(HTTPApiException.class)
    public ResponseEntity<String> handleHTTPApiException(HTTPApiException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(e.getHttpCode()));
    }

}