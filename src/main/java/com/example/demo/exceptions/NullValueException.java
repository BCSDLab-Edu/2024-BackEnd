package com.example.demo.exceptions;

import java.util.ArrayList;

public class NullValueException extends RuntimeException {
    public NullValueException(String message, ArrayList<String> nullList) {
        super("에러 코드 : 400" + '\n' + message + '\n' + "누락된 항목 : " + nullList);
    }
}