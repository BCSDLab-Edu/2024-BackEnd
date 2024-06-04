package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Member;

@Repository
public class MemberRepositoryJdbc implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password")
    );

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("""
            SELECT id, name, email, password
            FROM member
            """, memberRowMapper);
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> members = jdbcTemplate.query("""
            SELECT id, name, email, password
            FROM member
            WHERE id = ?
            """, memberRowMapper, id);
        return members.stream().findFirst();
    }

    @Override
    public Member insert(Member member) {
        jdbcTemplate.update("""
            INSERT INTO member (name, email, password)
            VALUES (?, ?, ?)
            """, member.getName(), member.getEmail(), member.getPassword());
        return member;
    }

    @Override
    public Member update(Member member) {
        jdbcTemplate.update("""
            UPDATE member
            SET name = ?, email = ?
            WHERE id = ?
            """, member.getName(), member.getEmail(), member.getId());
        return member;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("""
            DELETE FROM member
            WHERE id = ?
            """, id);
    }

    @Override
    public boolean existsByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject("""
            SELECT COUNT(*)
            FROM member
            WHERE email = ?
            """, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject("""
            SELECT COUNT(*)
            FROM member
            WHERE id = ?
            """, Integer.class, id);
        return count != null && count > 0;
    }
}
