package com.example.demo.exception;

public class MemberHasArticleException extends RuntimeException {
    public MemberHasArticleException(String msg) {
        super(msg);
    }
}
