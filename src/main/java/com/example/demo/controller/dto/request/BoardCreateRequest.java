package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BoardCreateRequest(
    @NotBlank(message = "게시판 명을 입력해주세요")
    String name

) {

}
