package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/*
@Repository
public class MemberRepositoryJpa implements MemberRepository {

    private EntityManager em;
    public MemberRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    @Transactional
    public Member insert(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    @Transactional
    public Member update(Member member) {
        return em.merge(member);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Member member = em.find(Member.class, id);
        if (member != null) {
            em.remove(member);
        }

    }

    @Override
    public boolean existsByEmail(String email) {
        Long count = em.createQuery("select count(m) from Member m where m.email= : email", Long.class)
            .setParameter("email", email)
            .getSingleResult();
        return count > 0;
    }
}*/
