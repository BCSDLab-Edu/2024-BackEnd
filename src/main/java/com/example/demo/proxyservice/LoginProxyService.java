package com.example.demo.proxyservice;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.entity.Member;
import com.example.demo.exception.HTTPApiException;
import com.example.demo.service.LoginService;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Service
public class LoginProxyService {

    private final LoginService loginservice;

    public LoginProxyService(LoginService loginservice) {
        this.loginservice = loginservice;
    }

    public String login(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            HttpServletRequest request
    ) throws HTTPApiException {
        if(email==null||password==null) {
            return "redirect:/login";
        }
        Member loginmember = loginservice.getMemberWithLogin(email, password);
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginmember);
        return "redirect:/showmethearticles";
    }

    public String myArticles(
            @SessionAttribute(name = "loginMember", required = false) Member member,
            Model model
    ) throws HTTPApiException {
        if(member==null) {
            return "redirect:/login";
        }
        model.addAttribute("articles", loginservice.getByMemberId(member.getId()));
        return "showmethearticles";
    }

    public String postSign(
            @RequestParam(required = false)String name,
            @RequestParam(required = false)String email,
            @RequestParam(required = false)String password
    ) throws HTTPApiException {
        if(name==null||email==null||password==null) {
            return "redirect:/regist";
        }
        if(loginservice.isExistEmail(email)) {
            return "redirect:/regist";
        }
        loginservice.create(new MemberCreateRequest(name, email, password));
        return "redirect:/login";
    }

}
