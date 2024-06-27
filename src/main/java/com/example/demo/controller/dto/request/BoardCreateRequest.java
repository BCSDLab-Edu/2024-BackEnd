package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record BoardCreateRequest(
    @NotNull(message = "이름을 입력해주세요.") String name
) {

}
