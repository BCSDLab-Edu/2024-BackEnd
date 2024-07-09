package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Article;
import com.example.demo.exception.HTTPApiException;
import com.example.demo.repository.MemberRepository;
import com.example.demo.util.SHA;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.entity.Member;

@Service
//@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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

    @Transactional
    public MemberResponse create(MemberCreateRequest request) throws HTTPApiException {
        Member member = memberRepository.insert(
            new Member(request.name(), request.email(), SHA.sha3_512(request.password()))
        );
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
        memberRepository.update(member);
        return MemberResponse.from(member);
    }

    public boolean isExistEmail(Long notthis, String email) {
        return memberRepository.isExistEmail(notthis, email);
    }

    public boolean isExistUser(Long userid) {
        return memberRepository.isExist(userid);
    }
}
