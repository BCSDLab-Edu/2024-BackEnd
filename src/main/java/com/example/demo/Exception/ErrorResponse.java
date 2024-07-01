package com.example.demo.Exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp=LocalDateTime.now();
    private int code_id;
    private String code;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.code_id = errorCode.getCode_id();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.code_id = errorCode.getCode_id();
        this.code = errorCode.getCode();
        this.message = message;
    }
}
