package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberCreateRequest(
    @NotNull(message = "이름을 입력해주세요.") String name,
    @NotNull(message = "이메일을 입력해주세요.") String email,
    @NotNull(message = "비밀번호를 입력해주세요") String password
) {

}
