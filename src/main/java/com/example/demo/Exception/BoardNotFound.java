package com.example.demo.Exception;

public class BoardNotFound extends RuntimeException {
    public BoardNotFound(String message) {
        super(message);
    }
}
