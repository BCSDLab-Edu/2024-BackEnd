package com.example.demo.exception;

public class BoardHasArticleException extends RuntimeException {
    public BoardHasArticleException(String msg) {
        super(msg);
    }
}
