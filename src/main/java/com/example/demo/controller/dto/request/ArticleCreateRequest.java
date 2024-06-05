package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record ArticleCreateRequest(
        @NotNull
        Long author_id,
        @NotNull
        Long board_id,
        @NotNull
        String title,
        @NotNull
        String content
) {

}
