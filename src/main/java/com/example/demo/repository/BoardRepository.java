package com.example.demo.repository;

import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT new com.example.demo.controller.dto.response.BoardResponse(board.id, board.name) " +
            "FROM Board board")
    List<BoardResponse> findAllBoards();

    @Query("SELECT new com.example.demo.controller.dto.response.BoardResponse(board.id, board.name) " +
            "FROM Board board WHERE board.id = :id")
    Optional<Board> findById(@Param("id") Long id);

    Board findBoardById(Long id);
}