/* package com.example.demo.repository.Jpa;

import com.example.demo.domain.Article;
import com.example.demo.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

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
        return em.createQuery("select a from Article a", Article.class).getResultList();
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        return em.createQuery("SELECT a from Article a where a.boardId=:boardId",Article.class).setParameter("boardId", boardId).getResultList();
    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        return em.createQuery("select a from Article a where a.authorId=:memberId",Article.class).setParameter("memberId", memberId).getResultList();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    public Article insert(Article article) {
        em.persist(article);
        return article;
    }

    @Override
    public Article update(Article article) {
        return em.merge(article);
    }

    @Override
    public void deleteById(Long id) {
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        }
    }

    @Override
    public boolean existByAuthorId(Long authorId) {
        return false;
    }

    @Override
    public boolean existByBoardId(Long boardId) {
        return false;
    }

}
*/