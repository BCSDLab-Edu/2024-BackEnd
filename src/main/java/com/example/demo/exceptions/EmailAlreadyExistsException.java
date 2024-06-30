package com.example.demo.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super("에러 코드 : 409" + '\n' + message);
    }
}