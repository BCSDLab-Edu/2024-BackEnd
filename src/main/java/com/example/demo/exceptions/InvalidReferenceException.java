package com.example.demo.exceptions;

public class InvalidReferenceException extends RuntimeException {
    public InvalidReferenceException(String message) {
        super("에러 코드 : 400" + '\n' + message);
    }
}