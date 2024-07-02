package com.example.demo.controller;

import java.util.List;

import com.example.demo.validate.ArticleValidate;
import com.example.demo.validate.MemberValidate;
import jakarta.validation.Valid;
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
    private final MemberValidate memberValidate;
    private final ArticleValidate articleValidate;

    public MemberController(MemberService memberService,
                            MemberValidate memberValidate,
                            ArticleValidate articleValidate) {
        this.memberService = memberService;
        this.memberValidate = memberValidate;
        this.articleValidate = articleValidate;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();
        memberValidate.validateResponseIsEmpty(response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> getMember(
        @PathVariable Long id
    ) {
        memberValidate.validateReadContainId(id);
        MemberResponse response = memberService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> create(
        @Valid @RequestBody MemberCreateRequest request
    ) {
        memberValidate.validateExistEmail(request.email());
        MemberResponse response = memberService.create(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(
        @PathVariable Long id,
        @Valid @RequestBody MemberUpdateRequest request
    ) {
        memberValidate.validateExistEmail(request.email());
        MemberResponse response = memberService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(
        @PathVariable Long id
    ) {
        articleValidate.validateExistAuthorId(id);

        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
