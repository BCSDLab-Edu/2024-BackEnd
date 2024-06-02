package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.errorcode.CommonErrorCode;
import com.example.demo.exception.exception.RestApiException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse getById(Long id) {
        Member member;

        try {
            member = memberRepository.findById(id);
        } catch(RuntimeException e) {
            throw new RestApiException(CommonErrorCode.GET_MEMBER_NOT_EXIST);
        }
            return MemberResponse.from(member);
    }

    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
            .map(MemberResponse::from)
            .toList();
    }

    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        Member member = memberRepository.insert(
            new Member(request.name(), request.email(), request.password())
        );
        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id);
        member.update(request.name(), request.email());
        try {
            memberRepository.update(member);
        } catch(RuntimeException e) {
            String errorMessage = e.getMessage();
            if(errorMessage.contains("Duplicate entry")) {
                throw new RestApiException(CommonErrorCode.EMAIL_ALREADY_EXIST);
            }
        }
        return MemberResponse.from(member);
    }
}
