package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.*;
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
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        Member member = memberRepository.findById(article.getAuthorId()).orElseThrow(MemberNotExistException::new);

        Board board = boardRepository.findById(article.getBoardId()).orElseThrow(BoardNotExistException::new);

        return ArticleResponse.of(article, member, board);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
            .map(article -> {
                Member member = memberRepository.findById(article.getAuthorId()).orElseThrow(MemberNotExistException::new);
                Board board = boardRepository.findById(article.getBoardId()).orElseThrow(BoardNotExistException::new);
                return ArticleResponse.of(article, member, board);
            })
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = new Article(
            request.authorId(),
            request.boardId(),
            request.title(),
            request.description()
        );

        if (request.boardId() == null || request.authorId() == null || request.title() == null || request.description() == null) {
            throw new RequestNullExistException();
        }

        Member member = memberRepository.findById(request.authorId()).orElseThrow(MemberNotExistException::new);

        Board board = boardRepository.findById(request.boardId()).orElseThrow(BoardNotExistException::new);

        Article saved = articleRepository.save(article);

        return ArticleResponse.of(saved, member, board);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        Member member = memberRepository.findById(request.authorId()).orElseThrow(MemberNotExistException::new);

        Board board = boardRepository.findById(request.boardId()).orElseThrow(BoardNotExistException::new);

        article.update(request.boardId(), request.title(), request.description());
        return ArticleResponse.of(article, member, board);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
