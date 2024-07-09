package com.example.demo.exception;

public interface BaseExceptionType {

    int getErrorCode();
    int getHttpStatus();
    String getErrorMessage();
}
