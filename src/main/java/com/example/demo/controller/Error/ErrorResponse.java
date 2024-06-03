package com.example.demo.controller.Error;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private String message;
    private int status;
    private String code;

    public ErrorResponse(final ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();

    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }



    public ErrorResponse(final ErrorCode errorCode, final List<FieldError> errors) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse of(final ErrorCode errorCode){
        return new ErrorResponse(errorCode);
    }

}
