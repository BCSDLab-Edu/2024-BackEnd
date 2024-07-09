package com.example.demo.repository;

import com.example.demo.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepositorySpringDataJpa extends JpaRepository<Board, Long> {

}
