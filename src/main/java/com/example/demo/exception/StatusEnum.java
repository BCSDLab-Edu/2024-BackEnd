package com.example.demo.exception;

public enum StatusEnum {
    READ_NOT_PRESENT_MEMBER(404, "Not Found", "멤버가 존재하지 않습니다."),
    READ_NOT_PRESENT_BOARD(404, "Not Found", "게시판이 존재하지 않습니다."),
    READ_NOT_PRESENT_ARTICLE(404, "Not Found", "게시물이 존재하지 않습니다."),
    CREATE_OR_EDIT_CONFLICT_EMAIL(409, "Conflict", "이미 존재하는 이메일 입니다."),
    EDIT_NOT_PRESENT_BOARD(400, "Bad Request", "존재하지 않는 내용을 참조하고 있습니다."),
    CREATE_OR_EDIT_EMPTY_REQUEST(400, "Bad Request", "요청 내용에 Null 또는 공백이 들어갔습니다."),
    CREATE_NOT_PRESENT_BOARD(400, "Bad Request", "존재하지 않는 내용을 참조하고 있습니다."),
    DELETE_MEMBER_PRESENT_ARTICLE(400, "Bad Request", "해당 사용자가 작성한 게시물이 존재합니다."),
    LOGIN_UNSUCCESSFUL(401, "Unauthorized", "로그인에 실패했습니다."),
    TOKEN_NULL(401, "Unauthorized", "토큰이 정상적이지 않습니다."),
    TOKEN_EXPIRED(401, "Unauthorized", "토큰의 유효시간이 만료되었습니다");


    private final Integer statusCode;
    private final String statusName;
    private final String statusMessage;

    private StatusEnum(Integer statusCode, String statusName, String statusMessage) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
