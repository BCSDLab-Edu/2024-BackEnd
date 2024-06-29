package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByBoardId(Long boardId);
    List<Article> findAllByAuthorId(Long authorId);
}
