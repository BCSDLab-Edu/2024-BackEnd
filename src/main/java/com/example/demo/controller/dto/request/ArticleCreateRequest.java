package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record ArticleCreateRequest(
        @NotNull Long authorId,
        @NotNull Long boardId,
        @NotNull String title,
        @NotNull String description
) {

}
