package com.example.demo.controller.dto.response;

import com.example.demo.domain.Member;

public record MemberResponse(
    Long id,
    String username,
    String email
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(), member.getEmail());
    }

}
