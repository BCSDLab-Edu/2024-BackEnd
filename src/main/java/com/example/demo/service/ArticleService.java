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
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("게시글 조회 실패"));
        Member member = memberRepository.findById(article.getAuthor().getId())
                .orElseThrow(()-> new MemberNotFoundException("사용자 조회 실패"));
        Board board = boardRepository.findById(article.getBoard().getId())
                .orElseThrow(()->new BoardNotFoundException("게시판을 찾을 수 없습니다."));
        return ArticleResponse.of(article, member, board);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);

        if (articles.isEmpty()) throw new ArticleNotFoundException("게시글 조회 실패");

        return articles.stream()
            .map(article -> {
                Member member = memberRepository.findById(article.getAuthor().getId())
                        .orElseThrow(()-> new MemberNotFoundException("사용자 조회 실패"));
                Board board = boardRepository.findById(article.getBoard().getId())
                        .orElseThrow(()->new BoardNotFoundException("게시판 조회 실패"));
                return ArticleResponse.of(article, member, board);
            })
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {

        Member member = memberRepository.findById(request.authorId())
            .orElseThrow(()->new InvalidMemberException("존재하지 않는 사용자입니다."));
        Board board = boardRepository.findById(request.boardId())
            .orElseThrow(()->new InvalidBoardException("존재하지 않는 게시판입니다."));

        Article article = new Article(member, board, request.title(), request.description());
        member.addArticle(article);
        board.addArticle(article);
        Article saved = articleRepository.save(article);

        return ArticleResponse.of(saved, member, board);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Board board = boardRepository.findById(request.boardId())
            .orElseThrow(()->new InvalidBoardException("존재하지 않는 게시판입니다."));

        Article article = articleRepository.findById(id)
                .orElseThrow();
        article.update(board, request.title(), request.description());
        Article updated = articleRepository.save(article);
        Member member = memberRepository.findById(updated.getAuthor().getId())
                .orElseThrow(()->new InvalidMemberException("존재하지 않는 사용자입니다."));

        return ArticleResponse.of(article, member, board);
    }

    @Transactional
    public void delete(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->new ArticleNotFoundException("게시글 조회 실패"));
        Member member = article.getAuthor();
        Board board = article.getBoard();
        member.removeArticle(article);
        board.removeArticle(article);
        articleRepository.deleteById(id);
    }
}
