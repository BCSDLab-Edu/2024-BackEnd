package com.example.demo.Exception;

public class FailMember extends RuntimeException{
    public FailMember(String message) {
        super(message);
    }
}
