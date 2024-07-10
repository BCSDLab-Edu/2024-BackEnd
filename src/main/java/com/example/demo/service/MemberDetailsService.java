package com.example.demo.service;

import com.example.demo.controller.dto.MemberDetails;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class MemberDetailsService implements UserDetailsService {


    private final MemberRepository memberRepository;


    @Autowired
    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("@@@@" + username);
        Member member = memberRepository.findMemberByUsername(username);
        System.out.println("@@@@" + member);
        if(member != null) {
            return new MemberDetails(member);
        }

        return null;
    }
}
