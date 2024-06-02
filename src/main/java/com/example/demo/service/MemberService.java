package com.example.demo.service;

import java.util.List;
import java.util.Objects;

import com.example.demo.Exception.ArticleExist;
import com.example.demo.Exception.EmailExist;
import com.example.demo.Exception.MemberNotFound;
import com.example.demo.repository.ArticleRepositoryJdbc;
import com.example.demo.repository.MemberRepositoryJdbc;
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
    private final MemberRepositoryJdbc memberRepositoryJdbc;
    private final ArticleRepositoryJdbc articleRepositoryJdbc;

    public MemberService(MemberRepository memberRepository, MemberRepositoryJdbc memberRepositoryJdbc, ArticleRepositoryJdbc articleRepositoryJdbc) {
        this.memberRepository = memberRepository;
        this.memberRepositoryJdbc = memberRepositoryJdbc;
        this.articleRepositoryJdbc = articleRepositoryJdbc;
    }

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(()-> new MemberNotFound("사용자를 찾을 수 없습니다."));
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
        if (articleRepositoryJdbc.existByAuthorId(id)) {
            throw new ArticleExist("게시물이 존재합니다.");
        }
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(()-> new MemberNotFound("사용자를 찾을 수 없습니다."));
        if(!member.getEmail().equals(request.email())&&memberRepositoryJdbc.existByEmail(request.email())) {
            throw new EmailExist("이메일이 이미 존재합니다.");
        }

        member.update(request.name(), request.email());
        memberRepository.update(member);
        return MemberResponse.from(member);
    }
}
