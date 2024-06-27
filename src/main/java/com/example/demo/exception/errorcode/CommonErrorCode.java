package com.example.demo.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    GET_ARTICLE_NOT_EXIST(HttpStatus.NOT_FOUND, "입력한 ID에 해당하는 게시물이 존재하지 않습니다."),
    GET_MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "입력한 ID에 해당하는 멤버가 존재하지 않습니다."),
    GET_BOARD_NOT_EXIST(HttpStatus.NOT_FOUND, "입력한 ID에 해당하는 게시판이 존재하지 않습니다."),
    POST_ARTICLE_NOT_EXIST(HttpStatus.BAD_REQUEST, "입력한 ID에 해당하는 게시물이 존재하지 않습니다."),
    POST_MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "입력한 ID에 해당하는 멤버가 존재하지 않습니다."),
    POST_BOARD_NOT_EXIST(HttpStatus.BAD_REQUEST, "입력한 ID에 해당하는 게시판이 존재하지 않습니다."),
    UPDATE_BOARD_NOT_EXIST(HttpStatus.BAD_REQUEST,"입력한 ID에 해당하는 게시판이 존재하지 않습니다." ), 
    UPDATE_NAME_NULL(HttpStatus.BAD_REQUEST, "변경할 이름을 입력해주세요"),
    UPDATE_EMAIL_NULL(HttpStatus.BAD_REQUEST, "변경할 이메일을 입력해주세요"),
    DELETE_ARTICLE_EXIST_IN_MEMBER(HttpStatus.BAD_REQUEST, "해당 멤버가 작성한 게시물이 존재합니다."),
    DELETE_ARTICLE_EXIST_IN_BOARD(HttpStatus.BAD_REQUEST, "해당 게시판에 게시물이 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
