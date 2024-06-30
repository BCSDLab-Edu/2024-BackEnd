package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    default Article insert(Article article) {
        return save(article);
    }

    default Article update(Article article) {
        return save(article);
    }

    default void deleteById(Long id) {
        delete(findById(id).get());
    }
}
