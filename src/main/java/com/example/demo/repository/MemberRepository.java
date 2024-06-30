package com.example.demo.repository;

import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT new com.example.demo.controller.dto.response.MemberResponse(member.id, member.name, member.email, member.password) " +
            "FROM Member member")
    List<MemberResponse> findAllMembers();

    @Query("SELECT new com.example.demo.controller.dto.response.MemberResponse(member.id, member.name, member.email, member.password) " +
            "FROM Member member WHERE member.id = :id")
    Optional<Member> findById(@Param("id") Long id);

    Member findMemberById(Long id);
}
