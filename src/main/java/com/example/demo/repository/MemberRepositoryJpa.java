package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepositoryJpa implements MemberRepositoryJdbc{

    private final EntityManager entityManager;

    public MemberRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    @Override
    public Member findById(Long id) {
        return entityManager.find(Member.class, id);
    }

    @Override
    public Member findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Member insert(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Member update(Member member) {
        return entityManager.merge(member);
    }

    @Override
    public void deleteById(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) entityManager.remove(member);
    }
}
