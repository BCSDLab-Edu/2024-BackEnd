package com.example.demo.controller.dto.request;

public record ArticleCreateRequest(
    Long author_id,
    Long board_id,
    String title,
    String content
) {

}
