package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@Setter
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "modifiedDate", nullable = false)
    private LocalDateTime modifiedAt;

    protected Article() {}

    public Article(
        Long id,
        Member author,
        Board board,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.author = author;
        this.board = board;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Article(Member author, Board board, String title, String content) {
        this.id = null;
        this.author = author;
        this.board = board;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void update(Board board, String title, String description) {
        this.board = board;
        this.title = title;
        this.content = description;
        this.modifiedAt = LocalDateTime.now();
    }


}
