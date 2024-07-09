package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.controller.dto.request.MemberLoginRequest;
import com.example.demo.controller.dto.response.LoginResponse;
import com.example.demo.domain.Article;
import com.example.demo.exception.CommonExceptionType;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.MemberExceptionType;
import com.example.demo.repository.ArticleRepositorySpringDataJpa;
import com.example.demo.repository.MemberRepositorySpringDataJpa;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepositorySpringDataJpa memberRepository;
    private final ArticleRepositorySpringDataJpa articleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepositorySpringDataJpa memberRepository,
                         ArticleRepositorySpringDataJpa articleRepository,
                         PasswordEncoder passwordEncoder,
                         JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MemberExceptionType.NOT_FOUND_MEMBER));

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
        if (!isValid(request))   throw new CustomException(CommonExceptionType.BAD_REQUEST_NULL_VALUE);

        String encodedPassword = passwordEncoder.encode(request.password());

        Member member = memberRepository.save(
            new Member(request.name(), request.email(), encodedPassword)
        );
        return MemberResponse.from(member);
    }

    private boolean isValid(MemberCreateRequest request) {
        return request.name() != null
                && request.email() != null
                && request.password() != null;
    }

    public LoginResponse login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        String message = member.getName() + "님 환영합니다";

        return new LoginResponse(message, jwtTokenProvider.generateToken(member.getEmail()));
    }

    @Transactional
    public void delete(Long id) {
        List<Article> articles = articleRepository.findAllByAuthor_Id(id);
        if (!articles.isEmpty()) throw new CustomException(MemberExceptionType.MEMBER_HAS_POSTS);

        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MemberExceptionType.NOT_FOUND_MEMBER));

        Optional<Member> DuplicatedMember = memberRepository.findByEmail(request.email());

        if (DuplicatedMember.isPresent())   throw new CustomException(MemberExceptionType.DUPLICATED_EMAIL);

        member.update(request.name(), request.email());
        memberRepository.save(member);
        return MemberResponse.from(member);
    }
}
