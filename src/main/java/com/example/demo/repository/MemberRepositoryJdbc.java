package com.example.demo.repository;

import com.example.demo.domain.Member;

public interface MemberRepositoryJdbc extends MemberRepository{

    Member findByEmail(String email);
}
