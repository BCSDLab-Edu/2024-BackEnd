package com.example.demo.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    ARTICLE_NOT_FOUND(404,"NOT FOUND", "해당 게시물을 찾을 수 없습니다."),
    BOARD_NOT_FOUND(404,"NOT FOUND", "해당 게시판을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(404, "NOT FOUND", "해당 사용자를 찾을 수 없습니다."),

    MEMBER_EMAIL_EXISTENCE(409,"CONFLICRT", "해당 이메일을 사용한 사용자가 이미 존재합니다."),
    INVALID_MEMBER(400, "BAD REQUEST", "존재하지 않는 사용자를 참조하고 있습니다."),
    INVALID_BOARD(400, "BAD REQUEST", "존재하지 않는 게시판을 참조하고 있습니다."),

    NULL_VALUE(400,"BAD REQUEST", "요청 중 null 값이 존재합니다."),

    MEMBER_HAS_ARTICLE(400, "BAD REQUEST", "사용자가 작성한 게시물이 존재합니다."),
    BOARD_HAS_ARTICLE(400, "BAD REQUEST", "게시판 내에 작성된 게시물이 존재합니다."),

    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR","서버 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
