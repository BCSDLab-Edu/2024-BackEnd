package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Board;

@Repository
public class BoardRepositoryJdbc implements BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public BoardRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Board> boardRowMapper = (rs, rowNum) -> new Board(
        rs.getLong("id"),
        rs.getString("name")
    );

    @Override
    public List<Board> findAll() {
        return jdbcTemplate.query("""
            SELECT id, name
            FROM board
            """, boardRowMapper);
    }

    @Override
    public Board findById(Long id) {
        return jdbcTemplate.queryForObject("""
            SELECT id, name
            FROM board
            WHERE id = ?
            """, boardRowMapper, id);
    }

    @Override
    public Board insert(Board board) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("""
                INSERT INTO board (name) VALUES (?)
                """, new String[]{"id"});
            ps.setString(1, board.getName());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("""
            DELETE FROM board WHERE id = ?
            """, id);
    }

    @Override
    public Board update(Board board) {
        return jdbcTemplate.queryForObject("""
            UPDATE board SET name = ? WHERE id = ?
            """, boardRowMapper, board.getName(), board.getId()
        );
    }

    private static final RowMapper<Boolean> findIsResult = new RowMapper<Boolean>() {
        @Override
        public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.next();
        }
    };

    @Override
    public Boolean isExistBoard(Long id) {
        return jdbcTemplate.queryForObject("""
                SELECT id FROM board WHERE id = ?
                """, findIsResult, id);
    }
}
