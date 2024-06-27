package com.example.demo.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
//    private Long authorId;
//    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String title;
    private String content;
    @Column(name = "created_date")
    private LocalDateTime createdAt;
    @Column(name = "modified_date")
    private LocalDateTime modifiedAt;

/*    public Article(
        Long id,
        Long authorId,
        Long boardId,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Article(Long authorId, Long boardId, String title, String content) {
        this.id = null;
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }*/

    public Article() {

    }

    public void update(Board board, String title, String description) {
        this.board = board;
        this.title = title;
        this.content = description;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setMember(Member member) {
        this.member = member;
        member.getArticles().add(this);
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getArticles().add(this);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

//    public Long getAuthorId() {
//        return authorId;
//    }
//
//    public Long getBoardId() {
//        return boardId;
//    }

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

    public Member getMember() {
        return member;
    }

    public Board getBoard() {
        return board;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public static Article createArticle(String title, String content, Member member, Board board) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setMember(member);
        article.setBoard(board);
        article.setCreatedAt(LocalDateTime.now());
        return article;
    }
}
