package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    private String title;
    private String content;
    @Column(name = "created_date")
    private LocalDateTime createdAt;
    @Column(name = "modified_date")
    private LocalDateTime modifiedAt;

    public Article() {
    }

    public Article(
        Long id,
        Member author,
        Board board,
        String title,
        String content
    ) {
        this.id = id;
        this.author = author;
        this.board = board;
        this.title = title;
        this.content = content;
    }

    public Article(Member author, Board board, String title, String content) {
        this.author = author;
        this.board = board;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void update(Member author, Board board, String title, String description) {
        this.author = author;
        this.board = board;
        this.title = title;
        this.content = description;
        this.modifiedAt = LocalDateTime.now();
    }

    public void convenienceMethod(Member author, Board board) {
        author.addArticle(this);
        board.addArticle(this);
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public Member getAuthor() {
        return author;
    }

    public Board getBoard() {
        return board;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}
