package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberCreateRequest(
    @NotNull String name,
    @NotNull String email,
    @NotNull String password
) {

}
