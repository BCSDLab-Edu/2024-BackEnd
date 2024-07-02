package com.example.demo.service;

import java.util.List;
import java.util.Objects;

import com.example.demo.domain.Article;

import com.example.demo.exception.BoardHasArticleException;
import com.example.demo.exception.BoardNotExistException;
import com.example.demo.exception.BoardNotFoundException;
import com.example.demo.exception.RequestNullExistException;

import com.example.demo.repository.ArticleRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.BoardCreateRequest;
import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;

import com.example.demo.domain.Board;
import com.example.demo.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public BoardService(ArticleRepository articleRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
    }

    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
            .map(BoardResponse::from)
            .toList();
    }

    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        return BoardResponse.from(board);
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        Board board = new Board(request.name());

        if(board.getName() == null) {
            throw new RequestNullExistException();
        }

        Board saved = boardRepository.save(board);
        return BoardResponse.from(saved);
    }

    @Transactional
    public void deleteBoard(Long id) {
        List<Article> allArticles = articleRepository.findAll();
        boolean boardHasArticles = allArticles.stream()
                .anyMatch(article -> Objects.equals(article.getAuthorId(), id));

        if (boardHasArticles) {
            throw new BoardHasArticleException();
        }
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotExistException::new);

        board.update(request.name());
        Board updated = boardRepository.save(board);
        return BoardResponse.from(updated);
    }
}
