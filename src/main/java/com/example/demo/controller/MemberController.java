package com.example.demo.controller;

import java.util.List;

import com.example.demo.controller.dto.request.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> getMember(
        @PathVariable Long id
    ) {
        MemberResponse response = memberService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> create(
        @Valid @RequestBody MemberCreateRequest request
    ) {
        MemberResponse response = memberService.create(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/members/login")
    public ResponseEntity<MemberResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        MemberResponse memberResponse = memberService.login(request);
        Cookie cookie = new Cookie("userId", memberResponse.id().toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(memberResponse);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(
        @PathVariable Long id,
        @RequestBody MemberUpdateRequest request
    ) {
        MemberResponse response = memberService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(
        @PathVariable Long id
    ) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
