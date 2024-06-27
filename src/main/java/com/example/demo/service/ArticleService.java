package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.exception.errorcode.CommonErrorCode;
import com.example.demo.exception.exception.RestApiException;
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
        Optional<Article> article;
        article = articleRepository.findById(id);
        return article.map(a -> ArticleResponse.of(a, a.getMember(), a.getBoard())).orElseThrow(() -> new RestApiException(CommonErrorCode.GET_ARTICLE_NOT_EXIST));
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
            .map(article -> {
                return ArticleResponse.of(article, article.getMember(), article.getBoard());
            })
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Member member;
        Board board;

        member = memberRepository.findById(request.authorId()).orElseThrow(() -> new RestApiException(CommonErrorCode.GET_MEMBER_NOT_EXIST));
        board = boardRepository.findById(request.boardId()).orElseThrow(() -> new RestApiException(CommonErrorCode.GET_BOARD_NOT_EXIST));

        Article article = Article.createArticle(request.title(), request.content(), member, board);
        Article saved = articleRepository.save(article);

        return ArticleResponse.of(saved, member, board);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RestApiException(CommonErrorCode.GET_ARTICLE_NOT_EXIST));
        Board board;
        board = boardRepository.findById(request.boardId()).orElseThrow(() -> new RestApiException(CommonErrorCode.GET_BOARD_NOT_EXIST));

        article.update(board, request.title(), request.description());
        return ArticleResponse.of(article, article.getMember(), board);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
