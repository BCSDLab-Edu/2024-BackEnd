package com.example.demo.exception;

public class InvalidBoardException extends RuntimeException {
    public InvalidBoardException(String msg) {
        super(msg);
    }
}