package com.example.demo.exception;

public class HTTPApiException extends Exception {

    private final ExceptionType type;

    public HTTPApiException(ExceptionType type) {
        this.type = type;
    }

    public int getHttpCode() {
        return type.getHttpstate();
    }

    public String getMessage() {
        return type.getMessage();
    }

}
