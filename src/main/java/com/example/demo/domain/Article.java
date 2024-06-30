package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Member author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    private LocalDateTime created_date;

    private LocalDateTime updated_date;

    @PrePersist
    protected void onCreate() {
        created_date = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_date = LocalDateTime.now();
    }


    public void update(Board board_id, String title, String content) {
        this.board = board_id;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Member getAuthor() {
        return this.author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public Board getBoard() {
        return this.board;
    }

    public Long getBoard_id() {
        return this.board.getId();
    }

    public Long getAuthor_id() {
        return this.author.getId();
    }

    ;

    public LocalDateTime getCreated_date() {
        return this.created_date;
    }

    public LocalDateTime getUpdated_date() {
        return this.updated_date;
    }
}