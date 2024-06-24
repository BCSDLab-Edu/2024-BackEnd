package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Article;
import com.example.demo.exception.errorcode.CommonErrorCode;
import com.example.demo.exception.exception.RestApiException;
import com.example.demo.repository.ArticleRepository;
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
    private final ArticleRepository articleRepository;

    public MemberService(MemberRepository memberRepository, ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
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
        // 삭제 시 article이 참조하고 있는 경우 삭제를 못하게 해야됨.
        Boolean isArticleExist = articleRepository.findAllByMemberId(id).stream()
                .findAny()
                .isPresent();

        if(isArticleExist) {
            throw new RestApiException(CommonErrorCode.DELETE_ARTICLE_EXIST_IN_MEMBER);
        }

        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id);
        member.update(request.name(), request.email());
        return MemberResponse.from(member);
    }
}
