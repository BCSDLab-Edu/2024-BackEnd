package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record BoardUpdateRequest(
    @NotNull String name
) {

}
