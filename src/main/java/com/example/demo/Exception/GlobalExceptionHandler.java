package com.example.demo.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArticleNotFound.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFound(ArticleNotFound articleNotFound) {
        ErrorResponse response=new ErrorResponse(ErrorCode.Article_Not_Found);
        return ResponseEntity.status(response.getCode_id()).body(response);
    }

    @ExceptionHandler(BoardNotFound.class)
    public ResponseEntity<ErrorResponse> handleBoardNotFound(BoardNotFound boardNotFound) {
        ErrorResponse response=new ErrorResponse(ErrorCode.Board_Not_Found);
        return ResponseEntity.status(response.getCode_id()).body(response);
    } 

    @ExceptionHandler(MemberNotFound.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFound(MemberNotFound memberNotFound) {
        ErrorResponse response=new ErrorResponse(ErrorCode.Member_Not_Found);
        return ResponseEntity.status(response.getCode_id()).body(response);
    }

    @ExceptionHandler(EmailExist.class)
    public ResponseEntity<ErrorResponse> handleEmailExist(EmailExist emailExist) {
        ErrorResponse response=new ErrorResponse(ErrorCode.Email_Exist);
        return ResponseEntity.status(response.getCode_id()).body(response);
    }

    @ExceptionHandler(FailMember.class)
    public ResponseEntity<ErrorResponse> handleFailMember(FailMember failMember) {
        ErrorResponse response=new ErrorResponse(ErrorCode.Fail_Member);
        return ResponseEntity.status(response.getCode_id()).body(response);
    }

    @ExceptionHandler(FailBoard.class)
    public ResponseEntity<ErrorResponse> handleFailBoard(FailBoard failBoard) {
        ErrorResponse response=new ErrorResponse(ErrorCode.Fail_Board);
        return ResponseEntity.status(response.getCode_id()).body(response);
    }

    @ExceptionHandler(NullExist.class)
    public ResponseEntity<ErrorResponse> handleNullExist(NullExist nullExist) {
        ErrorResponse response=new ErrorResponse(ErrorCode.Null_Exist);
        return ResponseEntity.status(response.getCode_id()).body(response);
    }

    @ExceptionHandler(ArticleExist.class)
    public ResponseEntity<ErrorResponse> handleArticleExist(ArticleExist articleExist) {
        ErrorResponse response=new ErrorResponse(ErrorCode.Remain_Article);
        return ResponseEntity.status(response.getCode_id()).body(response);
    }
}
