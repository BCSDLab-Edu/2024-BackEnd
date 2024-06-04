package com.example.demo.controller;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity getMember(
        @PathVariable Long id
    ) {
        try {
            MemberResponse response = memberService.getById(id);
            return ResponseEntity.ok(response);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾으려는 Member가 존재하지 않습니다.");
        }
    }

    @PostMapping("/members")
    public ResponseEntity create(
        @RequestBody MemberCreateRequest request
    ) {
        if (memberService.isNullExist(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null값은 허용되지 않습니다.");
        }

        MemberResponse response = memberService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity updateMember(
        @PathVariable Long id,
        @RequestBody MemberUpdateRequest request
    ) {
        if (memberService.isEmailExist(request.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일이 이미 존재합니다.");
        }

        MemberResponse response = memberService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity deleteMember(
        @PathVariable Long id
    ) {
        if (memberService.isExistArticle(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 작성자는 작성한 글이 존재하므로 삭제할 수 없습니다.");
        }
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
