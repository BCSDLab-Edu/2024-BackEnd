package com.example.demo.exception;

public enum PostExceptionType implements BaseExceptionType {

    NOT_FOUND_POST(1100, 404, "존재하지 않는 게시물입니다"),
    INVALID_MEMBER_REFERENCE(1101, 400, "유효하지않은 사용자에 대한 참조요청입니다"),
    INVALID_BOARD_REFERENCE(1102, 400, "유효하지않은 게시판에 대한 참조요청입니다");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    PostExceptionType(int errorCode, int httpStatus, String errorMessage) {
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
