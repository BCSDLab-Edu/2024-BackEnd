package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberUpdateRequest(
        @NotNull String name,
        @NotNull String email
) {

}
