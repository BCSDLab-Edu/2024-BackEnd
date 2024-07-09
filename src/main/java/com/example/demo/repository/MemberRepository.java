package com.example.demo.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member insert(Member member) {
        return save(member);
    }

    default Member update(Member member) {
        return save(member);
    }

    default void deleteById(Long id) {
        delete(findById(id).get());
    }

    @Query("SELECT p FROM Member p WHERE NOT p.id = :notthis AND p.email = :email")
    Member findByEmail(Long notthis, String email);

    @Query("SELECT p FROM Member p WHERE p.email = :email AND p.password = :pw")
    Member findWithLogin(String email, String pw);

    default Boolean isExistEmail(Long notthis, String email) {
        try{
            return findByEmail(notthis, email).getEmail().equalsIgnoreCase(email);
        }catch(Exception e) {
            return false;
        }
    }

    default boolean isExist(Long userid) {
        return findById(userid).isPresent();
    }
}
