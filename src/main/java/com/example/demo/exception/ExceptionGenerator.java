package com.example.demo.exception;

public class ExceptionGenerator extends RuntimeException{
    private final StatusEnum statusEnum;

    public ExceptionGenerator(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public Integer getStatusCode() {
        return statusEnum.getStatusCode();
    }

    public String getStatusName() {
        return statusEnum.getStatusName();
    }

    public String getStatusMessage() {
        return statusEnum.getStatusMessage();
    }
}
