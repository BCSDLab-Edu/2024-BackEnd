package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Board;

@Repository
@Primary
public class BoardRepositoryJdbc implements BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public BoardRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Board> findAll() {
        return jdbcTemplate.query("""
            SELECT id, name
            FROM board
            """, boardRowMapper);
    }
    private static final RowMapper<Board> boardRowMapper = (rs, rowNum) -> new Board(
            rs.getLong("id"),
            rs.getString("name")
    );

    @Override
    public Optional<Board> findById(Long id) {
        List<Board> boards = jdbcTemplate.query("""
            SELECT id, name
            FROM board
            WHERE id = ?
            """, boardRowMapper, id);
        return boards.stream().findFirst();
    }

    @Override
    public Board save(Board board) {
        jdbcTemplate.update("""
            INSERT INTO board (name)
            VALUES (?)
            """, board.getName());
        return board;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("""
            DELETE FROM board
            WHERE id = ?
            """, id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject("""
            SELECT COUNT(*)
            FROM board
            WHERE id = ?
            """, Integer.class, id);
        return count != null && count > 0;
    }
}
