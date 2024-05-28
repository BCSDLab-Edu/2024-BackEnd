package com.example.demo.controller.dto.request;

public record ArticleUpdateRequest(
    Long boardId,
    String title,
    String description
) {

}
