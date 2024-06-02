package com.example.demo.service;

import java.util.List;

import com.example.demo.Exception.ArticleExist;
import com.example.demo.Exception.BoardNotFound;
import com.example.demo.repository.ArticleRepositoryJdbc;
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
    private final ArticleRepositoryJdbc articleRepositoryJdbc;

    public BoardService(BoardRepository boardRepository, ArticleRepositoryJdbc articleRepositoryJdbc) {
        this.boardRepository = boardRepository;
        this.articleRepositoryJdbc = articleRepositoryJdbc;
    }

    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
            .map(BoardResponse::from)
            .toList();
    }

    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new BoardNotFound("게시판을 찾을 수 없습니다.");
        }
        return BoardResponse.from(board);
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        Board board = new Board(request.name());
        Board saved = boardRepository.insert(board);
        return BoardResponse.from(saved);
    }

    @Transactional
    public void deleteBoard(Long id) {
        if (articleRepositoryJdbc.existByBoardId(id))
            throw new ArticleExist("게시물이 존재합니다.");
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new BoardNotFound("게시판을 찾을 수 없습니다.");
        }
        board.update(request.name());
        Board updated = boardRepository.update(board);
        return BoardResponse.from(updated);
    }
}
