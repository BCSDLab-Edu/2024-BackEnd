package com.example.demo.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String msg) {
        super(msg);
    }
}
