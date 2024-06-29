package com.example.demo.exception;

public class EmailExistenceException extends RuntimeException {
    public EmailExistenceException(String msg) {
        super(msg);
    }
}
