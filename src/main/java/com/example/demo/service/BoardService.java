package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.BoardCreateRequest;
import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.domain.Board;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found with id " + id));
        return BoardResponse.from(board);
    }
    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
                .map(BoardResponse::from)
                .toList();
    }



    public List<BoardResponse> getAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(BoardResponse::from)
                .toList();
    }

    @Transactional
    public BoardResponse create(BoardCreateRequest request) {
        Board board = new Board(request.name());
        boardRepository.save(board);
        return BoardResponse.from(board);
    }

    @Transactional
    public void delete(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Board not found with id " + id);
        }
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found with id " + id));
        board.update(request.name());
        boardRepository.save(board);
        return BoardResponse.from(board);
    }
}
