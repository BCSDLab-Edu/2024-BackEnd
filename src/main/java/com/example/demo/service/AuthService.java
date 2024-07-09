package com.example.demo.service;

import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.validate.AuthValidate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthValidate authValidate;
    private final PasswordEncoder passwordEncoder;

    AuthService(MemberRepository memberRepository,
                AuthValidate authValidate,
                PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.authValidate = authValidate;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(MemberCreateRequest request) {
        Member member = new Member(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        );

        memberRepository.save(member);
    }

    public void logIn(LogInRequest request) {
        authValidate.validateLogInCorrectPassword(request);
    }
}
