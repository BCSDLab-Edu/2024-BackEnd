package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(
            ArticleRepository articleRepository,
            MemberRepository memberRepository,
            BoardRepository boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleResponse getById(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return ArticleResponse.of(article.get());
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = boardRepository.findById(boardId).get().getArticles();
        return articles.stream().map(ArticleResponse::of).toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Board board = boardRepository.findById(request.boardId()).get();
        Member author = memberRepository.findById(request.authorId()).get();

        Article article = new Article(author, board, request.title(), request.description());

        articleRepository.save(article);

        return ArticleResponse.of(article);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id).get();
        article.update(
                boardRepository.findById(request.boardId()).get(),
                request.title(),
                request.description()
        );

        articleRepository.save(article);

        return ArticleResponse.of(article);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }


}
