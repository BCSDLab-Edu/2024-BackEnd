package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Article;
import com.example.demo.exception.BoardExceptionType;
import com.example.demo.exception.CommonExceptionType;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.ArticleRepositorySpringDataJpa;
import com.example.demo.repository.BoardRepositorySpringDataJpa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.BoardCreateRequest;
import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.domain.Board;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepositorySpringDataJpa boardRepository;
    private final ArticleRepositorySpringDataJpa articleRepository;

    public BoardService(BoardRepositorySpringDataJpa boardRepository, ArticleRepositorySpringDataJpa articleRepository) {
        this.boardRepository = boardRepository;
        this.articleRepository = articleRepository;
    }

    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
            .map(BoardResponse::from)
            .toList();
    }

    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardExceptionType.NOT_FOUND_BOARD));

        return BoardResponse.from(board);
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        if (request.name() == null) throw new CustomException(CommonExceptionType.BAD_REQUEST_NULL_VALUE);

        Board board = new Board(request.name());
        Board saved = boardRepository.save(board);
        return BoardResponse.from(saved);
    }

    @Transactional
    public void deleteBoard(Long id) {
        /*
        영속성 전이 및 고아 객체 적용 확인을 위한 주석처리
        List<Article> articles = articleRepository.findAllByBoard_Id(id);
        if (!articles.isEmpty())    throw new CustomException(BoardExceptionType.BOARD_HAS_POSTS);
         */

        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BoardExceptionType.NOT_FOUND_BOARD));

        board.update(request.name());
        Board updated = boardRepository.save(board);
        return BoardResponse.from(updated);
    }
}
