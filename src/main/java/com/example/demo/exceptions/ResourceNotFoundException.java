package com.example.demo.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super("에러 코드 : 404" + '\n' + message);
    }
}