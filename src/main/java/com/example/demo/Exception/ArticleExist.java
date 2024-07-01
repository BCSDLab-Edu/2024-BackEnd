package com.example.demo.Exception;

public class ArticleExist extends RuntimeException{
    public ArticleExist(String message) {
        super(message);
    }
}
