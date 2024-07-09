package com.example.demo.exception;

public enum CommonExceptionType implements BaseExceptionType{

    BAD_REQUEST_NULL_VALUE(1000, 400, "요청 중 null 값이 존재합니다");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    CommonExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
