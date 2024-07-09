/*
package com.example.demo.repository;

import com.example.demo.domain.Board;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryJpa implements BoardRepository {

    private EntityManager em;

    public BoardRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    @Override
    @Transactional
    public Board insert(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Board board = em.find(Board.class, id);
        if (board != null) {
            em.remove(board);
        }
    }

    @Override
    @Transactional
    public Board update(Board board) {
        return em.merge(board);
    }
}
*/