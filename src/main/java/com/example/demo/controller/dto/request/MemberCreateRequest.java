package com.example.demo.controller.dto.request;

public record MemberCreateRequest(
    String name,
    String email,
    String password
) {

}
