package com.example.demo.controller.Error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOT_EXIST_ELEMENT(HttpStatus.NOT_FOUND, "존재하지 않는 요소입니다."),
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    NULL_ELEMENT(HttpStatus.BAD_REQUEST, "null인 값이 존재합니다. 다시 입력해주세요."),

    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자 또는 게시판을 참조하고 있습니다."),

    ARTICLE_EXIST(HttpStatus.BAD_REQUEST, "작성한 게시물이 존재합니다."),
    BOARD_EXIST(HttpStatus.BAD_REQUEST, "해당 게시판에 작성된 게시물이 존재합니다.");


    ErrorCode(HttpStatus status, String message) {
        this.message = message;
        this.status = status;

    }

    public HttpStatus getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }

    private final HttpStatus status;

    private final String message;
}
