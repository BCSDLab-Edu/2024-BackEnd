package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ConflictException;
import com.example.demo.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + id));
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
        if (memberRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already in use: " + request.email());
        }
        Member member = memberRepository.insert(
                new Member(request.name(), request.email(), request.password())
        );
        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id " + id);
        }
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + id));
        if (memberRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already in use: " + request.email());
        }
        member.update(request.name(), request.email());
        memberRepository.update(member);
        return MemberResponse.from(member);
    }
}
