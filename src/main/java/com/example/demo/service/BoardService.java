package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.BoardHasArticleException;
import com.example.demo.exception.BoardNotFoundException;
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

    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    public BoardService(BoardRepository boardRepository, ArticleRepository articleRepository) {
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
                .orElseThrow(() -> new BoardNotFoundException("게시판 조회 실패"));
        return BoardResponse.from(board);
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        Board board = new Board(request.name());
        Board saved = boardRepository.save(board);
        return BoardResponse.from(saved);
    }

    @Transactional
    public void deleteBoard(Long id) {
        if (articleRepository.findAllByBoardId(id) != null) {
            throw new BoardHasArticleException("게시판에 작성된 게시물이 존재합니다.");
        }
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()->new BoardNotFoundException("게시판 조회 실패"));
        board.update(request.name());
        Board updated = boardRepository.save(board);
        return BoardResponse.from(updated);
    }
}
