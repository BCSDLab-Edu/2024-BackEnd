package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    List<ErrorResponse.FieldError> fieldErrors = bindingResult.getFieldErrors().stream()
            .map(error -> new ErrorResponse.FieldError(error.getField(),error.getDefaultMessage()))
            .collect(Collectors.toList());
    ErrorResponse response = new ErrorResponse(ErrorCode.NULL_VALUE, fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_NOT_FOUND);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundException(ArticleNotFoundException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.ARTICLE_NOT_FOUND);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardNotFoundException(BoardNotFoundException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.BOARD_NOT_FOUND);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(EmailExistenceException.class)
    public ResponseEntity<ErrorResponse> handleEmailExistenceException(EmailExistenceException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_EMAIL_EXISTENCE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(InvalidMemberException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMemberException(InvalidMemberException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_MEMBER);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(InvalidBoardException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBoardException(InvalidBoardException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_BOARD);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MemberHasArticleException.class)
    public ResponseEntity<ErrorResponse> handleExistArticleException(MemberHasArticleException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_HAS_ARTICLE, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BoardHasArticleException.class)
    public ResponseEntity<ErrorResponse> handleExistArticleException(BoardHasArticleException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_HAS_ARTICLE, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
/*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
*/
}
