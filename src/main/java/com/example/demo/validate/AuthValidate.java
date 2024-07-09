package com.example.demo.validate;

import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthValidate {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    AuthValidate(MemberService memberService,
                 PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder  = passwordEncoder;
    }

    public void validateLogInCorrectPassword(LogInRequest request) {
        if (!passwordEncoder.matches(
                request.password(),
                memberService
                        .getByEmail(request.email())
                        .getPassword())
        )
            throw new ExceptionGenerator(StatusEnum.LOGIN_UNSUCCESSFUL);
    }
}