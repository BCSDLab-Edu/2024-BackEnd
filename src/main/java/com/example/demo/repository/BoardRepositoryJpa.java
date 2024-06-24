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
        return null;
    }

    @Override
    public Board insert(Board board) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Board update(Board board) {
        return null;
    }
}
