package com.example.demo.exception;

public class CustomException extends RuntimeException{

    private BaseExceptionType exceptionType;

    public CustomException(BaseExceptionType exceptionType) {
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
