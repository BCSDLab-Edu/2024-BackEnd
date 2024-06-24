package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Article;
import com.example.demo.exception.errorcode.CommonErrorCode;
import com.example.demo.exception.exception.RestApiException;
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
        Board board;

        try {
            board = boardRepository.findById(id);
        } catch (RuntimeException e) {
            throw new RestApiException(CommonErrorCode.GET_BOARD_NOT_EXIST);
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
        Boolean isArticleExist = articleRepository
                .findAllByBoardId(id)
                .stream()
                .findAny()
                .isPresent();
        if(isArticleExist) {
            throw new RestApiException(CommonErrorCode.DELETE_ARTICLE_EXIST_IN_BOARD);
        }
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id);
        board.update(request.name());
        return BoardResponse.from(board);
    }
}
