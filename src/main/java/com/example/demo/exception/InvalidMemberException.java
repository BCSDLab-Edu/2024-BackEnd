package com.example.demo.exception;

public class InvalidMemberException extends RuntimeException {
    public InvalidMemberException(String msg) {
        super(msg);
    }
}
