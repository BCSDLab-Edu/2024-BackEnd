package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class MemberRepositoryJpa implements MemberRepository{

    @PersistenceContext
    EntityManager em;

    @Override
    public Member findById(Long id) {
        Member member = em.find(Member.class, id);
        return member;
    }

    @Override
    public List<Member> findAll() {
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        return members;
    }

    @Override
    public Member insert(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Member update(Member member) {
        Member findMember = em.find(Member.class, member.getId());
        findMember.update(member.getName(), member.getEmail()); //Transactional의 변경감지
        return findMember;
    }

    @Override
    public void deleteById(Long id) {
        Member findMember = em.find(Member.class, id);
        em.remove(findMember);
    }
}
