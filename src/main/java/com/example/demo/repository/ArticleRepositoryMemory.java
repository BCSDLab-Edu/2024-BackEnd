package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.example.demo.domain.Article;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryMemory implements ArticleRepository {

    private static final Map<Long, Article> articles = new HashMap<>();
    private static final AtomicLong autoincrement = new AtomicLong(1);

    @Override
    public List<Article> findAll() {
        return articles.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        return articles.values().stream()
                .filter(article -> article.getBoardId().equals(boardId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> findAllByMemberId(Long memberId) {
        return articles.values().stream()
                .filter(article -> article.getAuthorId().equals(memberId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public Article insert(Article article) {
        long id = autoincrement.getAndIncrement();
        article.setId(id);
        articles.put(id, article);
        return article;
    }

    @Override
    public Article update(Article article) {
        articles.put(article.getId(), article);
        return article;
    }

    @Override
    public void deleteById(Long id) {
        articles.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return articles.containsKey(id);
    }
}
