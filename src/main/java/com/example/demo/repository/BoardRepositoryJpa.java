package com.example.demo.repository;

import com.example.demo.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class BoardRepositoryJpa implements BoardRepository{
    @PersistenceContext
    EntityManager em;

    @Override
    public Board findById(Long id) {
        Board board = em.find(Board.class, id);
        return board;
    }

    @Override
    public List<Board> findAll() {
        List<Board> boards = em.createQuery("select b from Board b", Board.class).getResultList();
        return boards;
    }

    @Override
    public Board insert(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    public Board update(Board board) {
        Board findBoard = em.find(Board.class, board.getId());
        findBoard.update(board.getName());
        return findBoard;
    }

    @Override
    public void deleteById(Long id) {
        Board board = em.find(Board.class, id);
        em.remove(board);
    }
}
