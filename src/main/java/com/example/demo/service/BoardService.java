package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Board;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardResponse> getAll() {
        return boardRepository.findAllBoards();
    }

    public Optional<Board> getById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public BoardResponse create(Board board) {
        Board saved = boardRepository.save(board);
        return new BoardResponse(saved.getId(), saved.getName());
    }


    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findBoardById(id);
        board.update(request.name);

        Board saved = boardRepository.save(board);
        return new BoardResponse(saved.getId(), saved.getName());
    }


    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}