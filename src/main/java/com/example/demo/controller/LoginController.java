package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.exception.HTTPApiException;
import com.example.demo.proxyservice.ArticleProxyService;
import com.example.demo.proxyservice.LoginProxyService;
import com.example.demo.proxyservice.MemberProxyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class LoginController {

    private final LoginProxyService loginService;
    private final ArticleProxyService articleService;

    public LoginController(LoginProxyService loginService, ArticleProxyService articleService) {
        this.loginService = loginService;
        this.articleService = articleService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String postLoginPage(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            HttpServletRequest request
    ) throws HTTPApiException {
        return loginService.login(email, password, request);
    }

    @GetMapping("/showmethearticles")
    public String myArticles(
            @SessionAttribute(name = "loginMember", required = false) Member member,
            Model model
    ) throws HTTPApiException {
        return loginService.myArticles(member, model);
    }

    @GetMapping("/sign")
    public String getSignPage() {
        return "sign";
    }

    @PostMapping("/sign")
    public String postSign(
            @RequestParam(required = false)String name,
            @RequestParam(required = false)String email,
            @RequestParam(required = false)String password
    ) throws HTTPApiException {
        return loginService.postSign(name, email, password);
    }
}
