package com.example.demo.service;

import java.util.List;

import com.example.demo.security.SecurityConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id).get();
        return MemberResponse.from(member);
    }

    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
            .map(MemberResponse::from)
            .toList();
    }

    public Member getByEmail(String email) {
        Member member = memberRepository.findByEmail(email).get();
        return member;
    }

    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        Member member = new Member(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).get();
        member.update(request.name(), request.email());

        memberRepository.save(member);

        return MemberResponse.from(member);
    }


}
