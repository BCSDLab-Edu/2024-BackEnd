package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Error> handleCustomException(CustomException e) {
        return new ResponseEntity<>(Error.create(e.getExceptionType()), HttpStatus.valueOf(e.getExceptionType().getHttpStatus()));
    }

     public static class Error {
        private int code;
        private int status;
        private String message;

        private Error() {}

        private Error(int code, int status, String message) {
            this.code = code;
            this.status = status;
            this.message = message;
        }

        static GlobalExceptionHandler.Error create(BaseExceptionType e) {
            return new GlobalExceptionHandler.Error(e.getErrorCode(), e.getHttpStatus(), e.getErrorMessage());
        }

        public int getCode() {
            return code;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
