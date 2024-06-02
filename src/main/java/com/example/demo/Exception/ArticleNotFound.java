package com.example.demo.Exception;

public class ArticleNotFound extends RuntimeException {
    public ArticleNotFound(String message) {
        super(message);
    }
}
