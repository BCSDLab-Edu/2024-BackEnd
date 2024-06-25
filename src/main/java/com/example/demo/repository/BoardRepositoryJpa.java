package com.example.demo.repository;

import com.example.demo.domain.Board;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryJpa implements BoardRepository {

    private final EntityManager entityManager;

    public BoardRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Board> findAll() {
        return entityManager.createQuery("SELECT b FROM Board b", Board.class).getResultList();
    }

    @Override
    public Board findById(Long id) {
        return entityManager.find(Board.class, id);
    }

    @Override
    public Board insert(Board board) {
        entityManager.persist(board);
        return board;
    }

    @Override
    public void deleteById(Long id) {
        Board board = entityManager.find(Board.class, id);
        if (board != null)  entityManager.remove(board);
    }

    @Override
    public Board update(Board board) {
        return entityManager.merge(board);
    }
}
