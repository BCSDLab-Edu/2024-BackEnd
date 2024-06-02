package com.example.demo.Exception;

public class FailBoard extends RuntimeException{
    public FailBoard(String message) {
        super(message);
    }
}
