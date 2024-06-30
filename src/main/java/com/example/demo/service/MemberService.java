
package com.example.demo.service;

import java.util.List;
import java.util.Optional;


import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Member;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<MemberResponse> getAll() {
        return memberRepository.findAllMembers();
    }

    public Optional<Member> getById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public MemberResponse create(Member member) {
        Member saved = memberRepository.save(member);
        return new MemberResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getPassword());
    }


    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findMemberById(id);
        member.update(request.name, request.email, request.password);

        Member saved = memberRepository.save(member);
        return new MemberResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getPassword());
    }


    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
