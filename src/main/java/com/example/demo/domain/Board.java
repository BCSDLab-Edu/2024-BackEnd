package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "board", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "UK_BOARD_NAME")
})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    protected Board() {}

    public Board(String name) {
        this.name = name;
    }

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void update(String name) {
        this.name = name;
    }
}
