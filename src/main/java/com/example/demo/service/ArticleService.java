package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;

import static com.example.demo.exception.BoardExceptionType.NOT_FOUND_BOARD;
import static com.example.demo.exception.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.example.demo.exception.PostExceptionType.*;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepositorySpringDataJpa articleRepository;
    private final MemberRepositorySpringDataJpa memberRepository;
    private final BoardRepositorySpringDataJpa boardRepository;

    public ArticleService(
        ArticleRepositorySpringDataJpa articleRepository,
        MemberRepositorySpringDataJpa memberRepository,
        BoardRepositorySpringDataJpa boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleResponse getById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_POST));

        Member member = article.getAuthor();
        Board board = article.getBoard();

        return ArticleResponse.of(article, member, board);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoard_Id(boardId);
        return articles.stream()
            .map(article -> {
                Member member = memberRepository.findById(article.getAuthorId())
                        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

                Board board = boardRepository.findById(article.getBoardId())
                        .orElseThrow(() -> new CustomException(NOT_FOUND_BOARD));
                return ArticleResponse.of(article, member, board);
            })
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        if (!isValid(request))  throw new CustomException(CommonExceptionType.BAD_REQUEST_NULL_VALUE);

        Member existedMember = memberRepository.findById(request.authorId())
                .orElseThrow(() -> new CustomException(INVALID_MEMBER_REFERENCE));

        Board existedBoard = boardRepository.findById(request.boardId())
                .orElseThrow(() -> new CustomException(INVALID_BOARD_REFERENCE));

        Article article = new Article(
            existedMember,
            existedBoard,
            request.title(),
            request.description()
        );

        Article saved = articleRepository.saveAndRefresh(article);
        Member member = memberRepository.findById(saved.getAuthorId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
        Board board = boardRepository.findById(saved.getBoardId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_BOARD));

        return ArticleResponse.of(saved, member, board);
    }

    private boolean isValid(ArticleCreateRequest request) {
        return request.authorId() != null
                && request.boardId() != null
                && request.title() != null
                && request.description() != null;
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_POST));

        Board existedBoard = boardRepository.findById(request.boardId())
                .orElseThrow(() -> new CustomException(INVALID_BOARD_REFERENCE));

        article.update(existedBoard, request.title(), request.description());

        Article updated = articleRepository.saveAndRefresh(article);
        Member member = memberRepository.findById(updated.getAuthorId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
        Board board = boardRepository.findById(article.getBoardId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_BOARD));

        return ArticleResponse.of(updated, member, board);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
