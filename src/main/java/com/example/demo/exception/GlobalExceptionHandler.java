package com.example.demo.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestNullExistException.class)
    public ResponseEntity<ErrorResponse> requestNullExistException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.REQUEST_NULL_VALUE);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(MemberNotExistException.class)
    public ResponseEntity<ErrorResponse> memberNotExistException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_NOT_EXIST);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(BoardNotExistException.class)
    public ResponseEntity<ErrorResponse> boardNotExistException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.BOARD_NOT_EXIST);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> articleNotFoundException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.ARTICLE_NOT_EXIST);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> memberNotFoundException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponse> boardNotFoundException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.BOARD_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(AlreadyHasEmailException.class)
    public ResponseEntity<ErrorResponse> alreadyHasEmailException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.EMAIL_EXIST);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler(MemberHasArticleException.class)
    public ResponseEntity<ErrorResponse> memberHasArticleException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_ARTICLE_EXIST);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(BoardHasArticleException.class)
    public ResponseEntity<ErrorResponse> boardHasArticleException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.BOARD_ARTICLE_EXIST);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
    }
}
