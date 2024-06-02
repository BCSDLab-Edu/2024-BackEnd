package com.example.demo.service;

import java.util.List;

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
        Article article;
        try {
            article = articleRepository.findById(id);
        } catch (RuntimeException e) {
            throw new RestApiException(CommonErrorCode.GET_ARTICLE_NOT_EXIST);
        }

        Member member = memberRepository.findById(article.getAuthorId());
        Board board = boardRepository.findById(article.getBoardId());
        return ArticleResponse.of(article, member, board);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
            .map(article -> {
                Member member = memberRepository.findById(article.getAuthorId());
                Board board = boardRepository.findById(article.getBoardId());
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
        Article saved = articleRepository.insert(article);

        Member member;
        Board board;
        try {
            member = memberRepository.findById(saved.getAuthorId());
        } catch(RuntimeException e) {
            throw new RestApiException(CommonErrorCode.POST_MEMBER_NOT_EXIST);
        }
        try {
            board = boardRepository.findById(saved.getBoardId());
        } catch(RuntimeException e) {
            throw new RestApiException(CommonErrorCode.POST_BOARD_NOT_EXIST);
        }
        // try-catch 문을 따로 사용하여 어디서 에러가 났는지 구분
        return ArticleResponse.of(saved, member, board);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id);
        article.update(request.boardId(), request.title(), request.description());
        Article updated = articleRepository.update(article);

        Member member = memberRepository.findById(updated.getAuthorId());

        Board board;
        try {
            board = boardRepository.findById(article.getBoardId());
        } catch(RuntimeException e) {
            throw new RestApiException(CommonErrorCode.UPDATE_BOARD_NOT_EXIST);
        }

        return ArticleResponse.of(article, member, board);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
