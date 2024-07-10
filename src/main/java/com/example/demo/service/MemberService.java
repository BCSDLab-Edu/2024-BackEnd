package com.example.demo.service;

import java.util.List;
import java.util.Objects;

import com.example.demo.domain.Article;
import com.example.demo.exception.*;
import com.example.demo.repository.ArticleRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(ArticleRepository articleRepository, MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
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
        if (request.name() == null || request.email() == null || request.password() == null) {
            throw new RequestNullExistException();
        }

        boolean emailExists = memberRepository.existsByEmail(request.email());

        if (emailExists) {
            throw new AlreadyHasEmailException();
        }

        Member member = memberRepository.save(
            new Member(request.name(), request.email(), bCryptPasswordEncoder.encode(request.password()), "ROLE_ADMIN")
        );

        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        List<Article> allArticles = articleRepository.findAll();
        boolean memberHasArticles = allArticles.stream()
                .anyMatch(article -> Objects.equals(article.getAuthorId(), id));

        if (memberHasArticles) {
            throw new MemberHasArticleException();
        }
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotExistException::new);

        boolean emailExists = memberRepository.existsByEmail(request.email());

        if (emailExists) {
            throw new AlreadyHasEmailException();
        }

        member.update(request.name(), request.email());
        memberRepository.save(member);
        return MemberResponse.from(member);
    }
}
