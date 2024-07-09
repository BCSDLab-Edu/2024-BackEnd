package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dto.request.LoginRequest;
import com.example.demo.exception.EmailExistenceException;
import com.example.demo.exception.MemberHasArticleException;
import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new MemberNotFoundException("사용자 조회 실패"));
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
            throw new EmailExistenceException("이미 존재하는 이메일입니다.");
        }

        Member member = new Member(request.name(), request.email(), passwordEncoder.encode(request.password())); // 비밀번호 암호화
        Member savedMember = memberRepository.save(member);
        return MemberResponse.from(savedMember);
    }

    public MemberResponse login(LoginRequest request) {
        Member member = memberRepository.findById(request.id())
            .orElseThrow(()-> new MemberNotFoundException("회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new MemberNotFoundException("비밀번호가 일치하지 않습니다.");
        }
        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        if(articleRepository.findAllByAuthorId(id) != null){
            throw new MemberHasArticleException("사용자가 작성한 게시물이 존재합니다.");
        }
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new MemberNotFoundException("사용자 조회 실패"));

        if (!member.getEmail().equals(request.email()) && memberRepository.existsByEmail(request.email())) {
            throw new EmailExistenceException("이미 존재하는 이메일입니다: " + request.email());
        }
        member.update(request.name(), request.email());
        memberRepository.save(member);
        return MemberResponse.from(member);
    }
}
