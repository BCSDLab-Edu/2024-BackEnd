package com.example.demo.controller.dto.request;

import jakarta.annotation.Nonnull;

public record ArticleCreateRequest(
        @Nonnull Long authorId,
        @Nonnull Long boardId,
        String title,
        String description
) {

}
