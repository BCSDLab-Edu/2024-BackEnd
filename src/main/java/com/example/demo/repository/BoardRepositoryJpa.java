package com.example.demo.repository;

import com.example.demo.domain.Board;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryJpa implements BoardRepository {

    private EntityManager em;

    public BoardRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    public Board insert(Board board) {
        em.persist(board);
        return board;
    }

    public Board update(Board board) {
        return em.merge(board);
    }

    public void deleteById(Long id) {
        Board b = em.find(Board.class,id);
        if(b!=null) {
            em.remove(b);
        }
    }
}
