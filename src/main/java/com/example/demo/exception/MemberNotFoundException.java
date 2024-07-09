package com.example.demo.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String msg) {
        super(msg);
    }
}
