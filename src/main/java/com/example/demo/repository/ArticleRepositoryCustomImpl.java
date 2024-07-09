package com.example.demo.repository;

import com.example.demo.domain.Article;
import jakarta.persistence.EntityManager;

public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final EntityManager entityManager;

    public ArticleRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Article saveAndRefresh(Article article) {
        entityManager.persist(article);
        entityManager.flush();
        entityManager.refresh(article);
        return article;
    }
}
