package com.example.demo.service;

import java.util.List;
import java.util.Objects;

import com.example.demo.domain.Article;
import com.example.demo.exception.*;
import com.example.demo.repository.ArticleRepository;

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

    public MemberService(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
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
        Member member = memberRepository.save(
            new Member(request.name(), request.email(), request.password())
        );

        if (member.getName() == null || member.getEmail() == null || member.getPassword() == null) {
            throw new RequestNullExistException();
        }
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

        boolean emailExists = memberRepository.findAll().stream()
                .anyMatch(m -> member.getEmail().equals(m.getEmail()));

        if (emailExists) {
            throw new AlreadyHasEmailException();
        }

        member.update(request.name(), request.email());
        memberRepository.save(member);
        return MemberResponse.from(member);
    }
}
