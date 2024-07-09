package com.example.demo.controller;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import com.example.demo.validate.MemberValidate;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    private final AuthService authService;
    private final MemberValidate memberValidate;
    private final JwtUtil jwtUtil;

    AuthController(AuthService authService,
                   MemberValidate memberValidate,
                   JwtUtil jwtUtil) {
        this.authService = authService;
        this.memberValidate = memberValidate;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody MemberCreateRequest request) {
        memberValidate.validateExistEmail(request.email());
        authService.register(request);

        System.out.println("회원가입 성공");
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> signIn(HttpServletResponse httpServletResponse, @RequestBody LogInRequest request) {
        authService.logIn(request);

        String token = jwtUtil.createJwt(request.email(), "USER", 60*60*1000L);
        httpServletResponse.addHeader("Authorization", "Bearer " + token);
        System.out.println("로그인 성공");

        return ResponseEntity.status(200).build();
    }
}
