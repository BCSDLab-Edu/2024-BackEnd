package com.example.demo.exceptions;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super("에러 코드 : 400" + '\n' + message);
    }
}