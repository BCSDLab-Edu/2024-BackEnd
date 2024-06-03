package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.repository.ArticleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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
        Member member = memberRepository.findById(id);
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
        memberRepository.update(member);
        return MemberResponse.from(member);
    }

    public boolean isNullExist(MemberCreateRequest request) {
        try{
            if (request.name() == null) return true;
            if (request.email() == null) return true;
            if (request.password() == null) return true;
            return false;
        }catch(EmptyResultDataAccessException e)
        {
            return true;
        }
    }

    public boolean isEmailExist(String email) {
        try{
            if (!(memberRepository.findByEmail(email) == null)) return true;
            return false;
        }catch(EmptyResultDataAccessException e)
        {
            return false;
        }
    }

    public boolean isExistArticle(long member_id) {
        try{
            if (!(articleRepository.findAllByMemberId(member_id).isEmpty())) return true;
            return false;
        }catch(EmptyResultDataAccessException e)
        {
            return false;
        }
    }
}
