package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Board;

public interface BoardRepository {

    List<Board> findAll();
    Optional<Board> findById(Long id); // Optional 반환 타입으로 수정

    Board save(Board board);

    void deleteById(Long id);

    boolean existsById(Long id); // 추가된 메서드
}
