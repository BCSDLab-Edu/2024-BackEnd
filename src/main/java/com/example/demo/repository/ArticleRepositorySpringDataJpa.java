package com.example.demo.repository;

import com.example.demo.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepositorySpringDataJpa extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    List<Article> findAllByBoard_Id(Long boardId);

    List<Article> findAllByAuthor_Id(Long authorId);
}
