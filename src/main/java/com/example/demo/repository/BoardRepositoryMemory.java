package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.domain.Board;
import org.springframework.stereotype.Repository;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class BoardRepositoryMemory implements BoardRepository {

    private static final Map<Long, Board> boards = new HashMap<>();
    private static final AtomicLong autoincrement = new AtomicLong(1);

    @Override
    public List<Board> findAll() {
        return boards.values().stream().collect(Collectors.toList());
    }
    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(boards.get(id));
    }

    @Override
    public Board save(Board board) {
        if (board.getId() == null) {
            long id = autoincrement.getAndIncrement();
            board.setId(id);
            boards.put(id, board);
        } else {
            boards.put(board.getId(), board);
        }
        return board;
    }

    @Override
    public void deleteById(Long id) {
        boards.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return boards.containsKey(id);
    }
}
