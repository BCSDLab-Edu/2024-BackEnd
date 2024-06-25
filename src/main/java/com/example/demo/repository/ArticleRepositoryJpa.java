package com.example.demo.repository;

import com.example.demo.domain.Article;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryJpa implements ArticleRepository {

    private final EntityManager entityManager;

    public ArticleRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Article> findAll() {
        return entityManager.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        return entityManager.createQuery("SELECT a FROM Article a WHERE a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        return entityManager.createQuery("SELECT a FROM Article a WHERE a.authorId = :memberId", Article.class)
                .setParameter("memberId", memberId)
                .getResultList();

    }

    @Override
    public Article findById(Long id) {
        return entityManager.find(Article.class, id);
    }

    @Override
    public Article insert(Article article) {
        entityManager.persist(article);
        entityManager.flush();
        entityManager.refresh(article);
        return article;
    }

    @Override
    public Article update(Article article) {
        Article mergedArticle = entityManager.merge(article);
        entityManager.flush();
        entityManager.refresh(mergedArticle);
        return mergedArticle;
    }

    @Override
    public void deleteById(Long id) {
        Article article = entityManager.find(Article.class, id);
        if (article != null)    entityManager.remove(article);
    }
}
