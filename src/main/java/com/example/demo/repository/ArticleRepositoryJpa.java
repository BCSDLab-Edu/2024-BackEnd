package com.example.demo.repository;

import com.example.demo.domain.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class ArticleRepositoryJpa implements ArticleRepository {

    @PersistenceContext
    EntityManager em;
    @Override
    public List<Article> findAll() {
        List<Article> articles = em.createQuery("select a from Article a", Article.class).getResultList();
        return articles;
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        TypedQuery<Article> query = em.createQuery("select a from Article a where a.boardId = :boardId", Article.class);
        query.setParameter("boardId", boardId);
        return query.getResultList();

    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        TypedQuery<Article> query = em.createQuery("select a from Article a where a.authorId = :authorId", Article.class);
        query.setParameter("authorId", memberId);
        return query.getResultList();
    }

    @Override
    public Article findById(Long id) {
        Article article = em.find(Article.class, id);
        return article;
    }

    @Override
    public Article insert(Article article) {
        em.persist(article);
        return article;
    }

    @Override
    public Article update(Article article) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Article article = em.find(Article.class, id);
        em.remove(article);
    }
}
