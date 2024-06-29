package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponse {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String code;
    private String message;
    private List<FieldError> errors;

    @Getter
    public static class FieldError {
        private String field;
        private String message;

        @Builder
        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = message;
    }

    public ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errors = errors;
    }
}
