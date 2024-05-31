package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.util.List;

import com.example.demo.exception.errorcode.CommonErrorCode;
import com.example.demo.exception.exception.RestApiException;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Member findById(Long id) {
        return jdbcTemplate.queryForObject("""
            SELECT id, name, email, password
            FROM member
            WHERE id = ?
            """, memberRowMapper, id);
    }

    @Override
    public Member insert(Member member) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("""
                
                        INSERT INTO member (name, email, password) VALUES (?, ?, ?)
                """, new String[]{
                "id"});
            ps.setString(1,
                member.getName());
            ps.
                setString(2, member.getEmail());
            ps.setString(3, member.getPassword());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue());
        } catch(RuntimeException e) { // 데이터 무결성 조건 위배
            throw new RestApiException(CommonErrorCode.EMAIL_ALREADY_EXIST);
        }
    }

    @Override
    public Member update(Member member) {
        try {
            jdbcTemplate.update("""
            
                    UPDATE member
            SET name = ?, email = ?
            WHERE id = ?
            """, member.getName(), member.getEmail(), member.getId
            ());
        return findById(member.getId());
        } catch (RuntimeException e) { // 데이터 무결성 조건 위배
            throw new RestApiException(CommonErrorCode.EMAIL_ALREADY_EXIST);
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("""
            DELETE FROM member
            WHERE id = ?
            """, id);
    }
}
