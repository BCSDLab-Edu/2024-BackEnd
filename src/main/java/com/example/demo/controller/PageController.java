package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.exception.HTTPApiException;
import com.example.demo.proxyservice.ArticleProxyService;
import com.example.demo.proxyservice.MemberProxyService;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
public class PageController {

    // 클라이언트에서 JavaScript를 이용해 API를 호출함.
    @GetMapping("/main")
    public String getBoardsPage() {
        return "boardList";
    }

    // 클라이언트에서 JavaScript를 이용해 API를 호출함.
    @GetMapping("/posts")
    public String getArticlesPage() {
        return "articleList";
    }

    @GetMapping("/posts/detail")
    public String getArticleDetailPage() {
        return "article";
    }

}
