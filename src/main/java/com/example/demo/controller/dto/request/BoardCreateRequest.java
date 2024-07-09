package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record BoardCreateRequest(
    @NotNull String name
) {

}
