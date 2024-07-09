package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record ArticleUpdateRequest(
        @NotNull Long boardId,
        @NotNull String title,
        @NotNull String description
        ) {

}
