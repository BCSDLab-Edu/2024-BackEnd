package com.example.demo.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "board")
    private List<Article> articles;

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Board(String name) {
        this.name = name;
    }

    public Board() {

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

    public List<Article> getArticles() {
        return articles;
    }
}
