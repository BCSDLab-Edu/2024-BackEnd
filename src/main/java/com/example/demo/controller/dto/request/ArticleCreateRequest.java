package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ArticleCreateRequest(
        @NotBlank(message = "작성자 ID를 입력해주세요")
        Long authorId,
        @NotBlank(message = "게시판 ID를 입력해주세요")
        Long boardId,
        @NotBlank(message = "제목을 입력해주세요")
        String title,
        @NotBlank(message = "내용을 입력해주세요")
        String description
) {

}
