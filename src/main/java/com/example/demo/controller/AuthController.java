package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    @Lazy
    private MemberRepository memberRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String createLoginToken(@RequestBody Member member) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword()));
        } catch (AuthenticationException e) {
            throw new Exception("이메일 또는 비밀번호가 일치하지 않습니다.", e);
        }
        return jwtUtil.generateToken(member.getEmail());
    }
}