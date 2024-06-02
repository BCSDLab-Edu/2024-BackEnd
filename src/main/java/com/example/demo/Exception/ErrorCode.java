package com.example.demo.Exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    Article_Not_Found(404,"NOT_FOUND","게시물을 찾을 수 없습니다."),
    Board_Not_Found(404,"NOT_FOUND","게시판을 찾을 수 없습니다."),
    Member_Not_Found(404,"NOT_FOUND","사용자를 찾을 수 없습니다."),

    Email_Exist(409,"Exist","이미 이메일이 존재합니다."),
    Fail_Member(400,"FAIL","사용자를 참조할 수 없습니다."),
    Fail_Board(400,"FAIL","게시판을 참조할 수 없습니다."),

    Null_Exist(400,"NULL","빈(Null) 값이 존재합니다."),

    Remain_Article(400,"REMAIN","게시물이 남아있습니다.");

    private final int code_id;
    private final String code;
    private final String message;

    ErrorCode(int code_id, String code, String message) {
        this.code_id = code_id;
        this.code = code;
        this.message = message;
    }
}
