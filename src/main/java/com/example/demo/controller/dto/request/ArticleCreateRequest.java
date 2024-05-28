package com.example.demo.controller.dto.request;

public record ArticleCreateRequest(
    Long authorId,
    Long boardId,
    String title,
    String description
) {

}
