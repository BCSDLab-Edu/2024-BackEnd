package com.example.demo.exception;

public enum BoardExceptionType implements BaseExceptionType {

    NOT_FOUND_BOARD(1200, 404, "존재하지 않는 게시판입니다"),
    BOARD_HAS_POSTS(1201, 400, "게시판에 게시물이 존재합니다");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;
    BoardExceptionType(int errorCode, int httpStatus, String errorMessage) {
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
