package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Article;

public interface ArticleRepository {

    List<Article> findAll();

    List<Article> findAllByBoardId(Long boardId);

    List<Article> findAllByMemberId(Long memberId);

    Article findById(Long id);

    Article insert(Article article);

    Article update(Article article);

    void deleteById(Long id);

    boolean existByAuthorId(Long authorId);

    boolean existByBoardId(Long boardId);
}
