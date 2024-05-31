package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record BoardCreateRequest(
    @NotNull(message = "게시판 명을 입력해주세요")
    String name

) {

}
