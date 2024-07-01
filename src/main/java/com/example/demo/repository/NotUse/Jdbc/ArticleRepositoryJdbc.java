/* package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import com.example.demo.Exception.ArticleNotFound;
import com.example.demo.Exception.NullExist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Article;

@Repository
public class ArticleRepositoryJdbc implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Article> articleRowMapper = (rs, rowNum) -> new Article(
            rs.getLong("id"),
            rs.getLong("author_id"),
            rs.getLong("board_id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getTimestamp("created_date").toLocalDateTime(),
            rs.getTimestamp("modified_date").toLocalDateTime()
    );

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("""
            SELECT id,  board_id,  author_id,  title,  content,  created_date,  modified_date
            FROM article
            """, articleRowMapper);
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        return jdbcTemplate.query("""
            SELECT id,  board_id,  author_id,  title,  content,  created_date,  modified_date
            FROM article
            WHERE board_id = ?
            """, articleRowMapper, boardId);
    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        return jdbcTemplate.query("""
            SELECT id,  board_id,  author_id,  title,  content,  created_date,  modified_date
            FROM article
            WHERE author_id = ?
            """, articleRowMapper, memberId);
    }

    @Override
    public Optional<Article> findById(Long id) {
        try {
            Article article = jdbcTemplate.queryForObject("""
            SELECT id,  board_id,  author_id,  title,  content,  created_date,  modified_date
            FROM article
            WHERE id = ?
            """, articleRowMapper, id);
            return Optional.ofNullable(article);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Article insert(Article article) {
        if (article.getBoardId() == null) {
            throw new NullExist("게시판 아이디가 존재하지 않습니다.");
        }
        if (article.getAuthorId() == null) {
            throw new NullExist("사용자 아이디가 존재하지 않습니다.");
        }
        if (article.getTitle() == null) {
            throw new NullExist("제목이 존재하지 않습니다.");
        }
        if (article.getContent() == null) {
            throw new NullExist("내용이 존재하지 않습니다.");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("""
                    INSERT INTO article (board_id, author_id, title, content)
                    VALUES (?, ?, ?, ?)
                    """,
                    new String[]{"id"});
            ps.setLong(1, article.getBoardId());
            ps.setLong(2, article.getAuthorId());
            ps.setString(3, article.getTitle());
            ps.setString(4, article.getContent());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue()).orElseThrow(()->new ArticleNotFound("게시글을 찾을 수 없습니다."));
    }

    @Override
    public Article update(Article article) {
        jdbcTemplate.update("""
                    UPDATE article
                    SET board_id = ?, title = ?, content = ?
                    WHERE id = ?
                    """,
                article.getBoardId(),
                article.getTitle(),
                article.getContent(),
                article.getId()
        );
        return findById(article.getId()).orElseThrow(()-> new ArticleNotFound("게시글을 찾을 수 없습니다."));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("""
                DELETE FROM article
                WHERE id = ?
                """, id);
    }

    @Override
    public boolean existByAuthorId(Long authorId) {
        Integer count=jdbcTemplate.queryForObject("""
                    SELECT COUNT(*)
                    FROM article
                    WhERE author_id=?
                    """,Integer.class,authorId);
        return count !=null && count>0;
    }

    @Override
    public boolean existByBoardId(Long boardId) {
        Integer count =jdbcTemplate.queryForObject("""
                    SELECT COUNT(*)
                    FROM article
                    WHERE author_id=?
                    """,Integer.class,boardId);
        return count !=null && count>0;
    }
}
*/