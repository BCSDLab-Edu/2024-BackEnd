/*
package com.example.demo.repository;

import com.example.demo.domain.Article;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryJpa implements ArticleRepository {

    private final EntityManager em;

    public ArticleRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        return em.createQuery("select a from Article a where a.boardId = :boardId", Article.class)
            .setParameter("boardId", boardId)
            .getResultList();
    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        return em.createQuery("select a from Article a where a.authorId = :authorId", Article.class)
            .setParameter("authorId", memberId)
            .getResultList();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    @Transactional
    public Article insert(Article article) {
        em.persist(article);
        return article;
    }

    @Override
    @Transactional
    public Article update(Article article) {
        return em.merge(article);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Article article= em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        }
    }
}
*/
