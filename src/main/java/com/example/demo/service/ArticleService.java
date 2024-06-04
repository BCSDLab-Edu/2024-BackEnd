package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
        Member member = findMemberById(article.getAuthorId());
        Board board = findBoardById(article.getBoardId());
        return ArticleResponse.of(article, member, board);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
                .map(article -> {
                    Member member = findMemberById(article.getAuthorId());
                    Board board = findBoardById(article.getBoardId());
                    return ArticleResponse.of(article, member, board);
                })
                .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        if (!memberRepository.existsById(request.authorId())) {
            throw new ResourceNotFoundException("Author not found with id " + request.authorId());
        }
        if (!boardRepository.existsById(request.boardId())) {
            throw new ResourceNotFoundException("Board not found with id " + request.boardId());
        }
        Article article = new Article(request.authorId(), request.boardId(), request.title(), request.description());
        articleRepository.insert(article);
        Member member = findMemberById(request.authorId());
        Board board = findBoardById(request.boardId());
        return ArticleResponse.of(article, member, board);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
        if (!memberRepository.existsById(article.getAuthorId())) {
            throw new ResourceNotFoundException("Author not found with id " + article.getAuthorId());
        }
        if (!boardRepository.existsById(article.getBoardId())) {
            throw new ResourceNotFoundException("Board not found with id " + article.getBoardId());
        }
        article.update(request.boardId(), request.title(), request.description());
        articleRepository.update(article);
        Member member = findMemberById(article.getAuthorId());
        Board board = findBoardById(article.getBoardId());
        return ArticleResponse.of(article, member, board);
    }

    @Transactional
    public void delete(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Article not found with id " + id);
        }
        articleRepository.deleteById(id);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + memberId));
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found with id " + boardId));
    }
}
