package com.example.demo.repository;

import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT new com.example.demo.controller.dto.response.ArticleResponse(article.id, article.title, article.content, article.author.id, article.board.id, article.created_date, article.updated_date) " +
            "FROM Article article WHERE article.board.id = :id")
    List<ArticleResponse> findByBoardId(@Param("id") Long boardId);

    @Query("SELECT new com.example.demo.controller.dto.response.ArticleResponse(article.id, article.title, article.content, article.author.id, article.board.id, article.created_date, article.updated_date) " +
            "FROM Article article WHERE article.id = :id")
    Optional<Article> findById(@Param("id") Long id);

    @Query("SELECT new com.example.demo.controller.dto.response.ArticleResponse(article.id, article.title, article.content, article.author.id, article.board.id, article.created_date, article.updated_date) " +
            "FROM Article article")
    List<ArticleResponse> findAllArticles();

    Article findArticleById(Long id);
}