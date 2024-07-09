package com.example.demo.service;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.exception.HTTPApiException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.util.SHA;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class LoginService {

    private final MemberRepository memberRepository;

    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMemberWithLogin(String email, String pw) throws HTTPApiException {
        return memberRepository.findWithLogin(email, SHA.sha3_512(pw));
    }

    public boolean isExistEmail(String email) {
        return memberRepository.isExistEmail(-1L, email);
    }

    @Transactional
    public void create(MemberCreateRequest request) throws HTTPApiException {
        Member member = memberRepository.insert(
                new Member(request.name(), request.email(), SHA.sha3_512(request.password()))
        );
    }

    public List<ArticleResponse> getByMemberId(Long memberId) {
        List<Article> articles = memberRepository.findById(memberId).get().getArticles();
        return articles.stream()
                .map(ArticleResponse::of)//인텔리제이가 이렇게 바꿔주더라구요.
                .toList();
    }

}
