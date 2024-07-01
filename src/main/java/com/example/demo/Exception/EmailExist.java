package com.example.demo.Exception;

public class EmailExist extends RuntimeException {
    public EmailExist(String message) {
        super(message);
    }
}
