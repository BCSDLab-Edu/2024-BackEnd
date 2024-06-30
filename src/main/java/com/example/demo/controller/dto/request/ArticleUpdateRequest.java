package com.example.demo.controller.dto.request;

public record ArticleUpdateRequest(
    Long authorId,
    Long boardId,
    String title,
    String description
) {

}
