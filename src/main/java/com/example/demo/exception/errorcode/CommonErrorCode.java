package com.example.demo.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    ARTICLE_NOT_EXIST(HttpStatus.NOT_FOUND, "입력한 ID에 해당하는 게시물이 존재하지 않습니다."),
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "입력한 ID에 해당하는 멤버가 존재하지 않습니다."),
    BOARD_NOT_EXIST(HttpStatus.NOT_FOUND, "입력한 ID에 해당하는 게시판이 존재하지 않습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
