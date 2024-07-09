package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record LogInRequest(
        @NotNull String email,
        @NotNull String password
) {

}
