package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="article")
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name="title", nullable=false)
    private String title;

    @Column(name="content", nullable=false)
    private String content;

    @Column(name="created_date", nullable=false)
    private LocalDateTime createdAt;

    @Column(name="modified_date", nullable=false)
    private LocalDateTime modifiedAt;

    public Article(
        Long id,
        Member member,
        Board board,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.member = member;
        this.board = board;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Article(Member member, Board board, String title, String content) {
        this.id = null;
        this.member = member;
        this.board = board;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public Article() {

    }

    public void update(Board board, String title, String description) {
        this.board = board;
        this.title = title;
        this.content = description;
        this.modifiedAt = LocalDateTime.now();
    }


}
