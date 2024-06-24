package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryJpa implements MemberRepository {

    private EntityManager em;

    public MemberRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }


    public Member insert(Member member) {
        em.persist(member);
        return member;
    }

    public Member update(Member member) {
        return em.merge(member);
    }

    public void deleteById(Long id) {
        Member m=em.find(Member.class, id);
        if (m!=null) {
            em.remove(m);
        }
    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }
}
