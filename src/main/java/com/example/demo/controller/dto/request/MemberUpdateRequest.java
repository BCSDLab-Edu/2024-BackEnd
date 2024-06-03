package com.example.demo.controller.dto.request;

import lombok.Getter;

public record MemberUpdateRequest(
    String name,
    String email
) {

}
