package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.Member;

public interface MemberRepository {

    List<Member> findAll();

    Member findById(Long id);

    Member insert(Member member);

    Member update(Member member);

    void deleteById(Long id);

    Boolean isExistEmail(Long notthis,String email);
}
