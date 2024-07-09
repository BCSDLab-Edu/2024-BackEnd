package com.example.demo.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_date", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modified_date", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;

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
    }

    protected Article() {}

    public void update(Board board, String title, String description) {
        this.board = board;
        this.title = title;
        this.content = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Member getAuthor() {
        return author;
    }

    public Long getAuthorId() {
        return author.getId();
    }

    public Board getBoard() {
        return board;
    }

    public Long getBoardId() {
        return board.getId();
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
