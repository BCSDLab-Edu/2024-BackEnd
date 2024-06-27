package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberUpdateRequest(
    @NotNull(message = "멤버의 이름을 적어주세요.") String name,
    @NotNull(message = "멤버의 이메일을 적어주세요") String email
) {

}
