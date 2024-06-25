package com.example.demo.exception;

public enum MemberExceptionType implements BaseExceptionType {

    NOT_FOUND_MEMBER(1300, 404, "존재하지 않는 사용자입니다"),
    MEMBER_HAS_POSTS(1301, 400, "사용자가 작성한 게시물이 존재합니다"),
    DUPLICATED_EMAIL(1302, 409, "이미 존재하는 이메일입니다");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, int httpStatus, String errorMessage) {
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
