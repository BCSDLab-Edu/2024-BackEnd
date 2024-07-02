package com.example.demo.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    REQUEST_NULL_VALUE(400,"BAD REQUEST", "요청 메시지에 null 값이 존재합니다."),
    BOARD_NOT_EXIST(400, "BAD REQUEST", "해당 게시판을 찾을 수 없습니다."),
    MEMBER_NOT_EXIST(400, "BAD REQUEST", "해당 사용자를 찾을 수 없습니다."),

    ARTICLE_NOT_EXIST(404,"NOT FOUND", "해당 게시물을 찾을 수 없습니다."),
    BOARD_NOT_FOUND(404, "NOT FOUND", "해당 게시판을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(404, "NOT FOUND", "해당 사용자를 찾을 수 없습니다."),

    EMAIL_EXIST(409,"CONFLICT", "해당 이메일이 존재합니다."),
    INVALID_MEMBER_OR_BOARD(400, "BAD REQUEST", "존재하지 않는 사용자, 게시판을 참조하고 있습니다."),

    BOARD_ARTICLE_EXIST(400, "BAD REQUEST", "게시판에 작성된 게시물이 존재합니다."),
    MEMBER_ARTICLE_EXIST(400, "BAD REQUEST", "사용자가 작성한 게시물이 존재합니다.");

    private final int code;
    private final String status;
    private final String message;

    ErrorCode(int code, String status, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
