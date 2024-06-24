package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.util.List;

import com.example.demo.entity.Board;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Article;

@Repository
public class ArticleRepositoryJdbc extends com.example.demo.repository.Repository<Article> {

    private final EntityManager entityManager;

    public ArticleRepositoryJdbc(EntityManager jdbcTemplate) {
        this.entityManager = jdbcTemplate;
    }

    @Override
    public List<Article> findAll() {
        return entityManager.createQuery("SELECT p FROM Article p", Article.class).getResultList();
    }

    public List<Article> findAllByBoardId(Long boardId) {
        return entityManager.createQuery(
                new StringBuilder("SELECT p FROM Article p WHERE p.boardId = ")
                        .append(boardId).toString(), Article.class)
                .getResultList();
    }

    public List<Article> findAllByMemberId(Long memberId) {
        return entityManager.createQuery(
                        new StringBuilder("SELECT p FROM Article p WHERE p.authorId = ")
                                .append(memberId).toString(), Article.class)
                .getResultList();
    }

    @Override
    public Article findById(Long id) {
        return entityManager.find(Article.class, id);
    }

    @Override
    public Article insert(Article article) {
        entityManager.persist(article);
        return findById(article.getId());
    }

    @Override
    public Article update(Article article) {
        entityManager.persist(article);
        return findById(article.getId());
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
