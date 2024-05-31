package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import com.example.demo.exception.MemberNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        try{
            Member member = jdbcTemplate.queryForObject("""
            SELECT id, name, email, password
            FROM member
            WHERE id = ?
            """, memberRowMapper, id);
            return Optional.ofNullable(member);
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject("""
            SELECT COUNT(*)
            FROM member
            WHERE email =?
            """, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public Member insert(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("""
                INSERT INTO member (name, email, password) VALUES (?, ?, ?)
                """, new String[]{"id"});
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue())
                .orElseThrow(()->new MemberNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public Member update(Member member) {
        jdbcTemplate.update("""
            UPDATE member
            SET name = ?, email = ?
            WHERE id = ?
            """, member.getName(), member.getEmail(), member.getId());
        return findById(member.getId())
                .orElseThrow(()->new MemberNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("""
            DELETE FROM member
            WHERE id = ?
            """, id);
    }
}
