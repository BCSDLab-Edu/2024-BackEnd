package com.example.demo.service;

import com.example.demo.controller.dto.request.CustomUserDetails;
import com.example.demo.domain.Member;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //DB에서 조회
        Member member = memberRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new ExceptionGenerator(StatusEnum.LOGIN_UNSUCCESSFUL)
                );

        return new CustomUserDetails(member.getEmail(), "USER");
    }
}