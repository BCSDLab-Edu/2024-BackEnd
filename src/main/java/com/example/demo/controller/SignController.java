package com.example.demo.controller;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignController {

    private final MemberService memberService;

    @Autowired
    public SignController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members/register")
    public String register(MemberCreateRequest request) {
        MemberResponse response = memberService.create(request);
        ResponseEntity.ok(response);
        return "redirect:/members/signin";
    }

    @PostMapping("/members/view")
    public String login() {
        return "redirect:/members/home";
    }

    @GetMapping("/members/signup")
    public String signUp() {
        return "signup";
    }

    @GetMapping("/members/signin")
    public String signIn() {
        return "signin";
    }

    @GetMapping("/members/home")
    public String home(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("id", id);
        return "home";
    }

    @GetMapping("/members/admin")
    public String admin() {
        return "admin";
    }

}
