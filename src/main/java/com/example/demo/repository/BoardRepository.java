package com.example.demo.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    default Board insert(Board board) {
        return save(board);
    }

    default void deleteById(Long id) {
        delete(findById(id).get());
    }

    default Board update(Board board) {
        return save(board);
    }

    default boolean isExist(Long id) {
        return findById(id).isPresent();
    }
}
