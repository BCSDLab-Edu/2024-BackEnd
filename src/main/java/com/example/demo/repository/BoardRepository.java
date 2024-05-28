package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.Board;

public interface BoardRepository {

    List<Board> findAll();

    Board findById(Long id);

    Board insert(Board board);

    void deleteById(Long id);

    Board update(Board board);
}
