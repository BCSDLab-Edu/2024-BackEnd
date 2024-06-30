package com.example.demo.controller.dto.response;

import java.time.LocalDateTime;

import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private Long author_id;
    private Long board_id;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;


    // 생성자
    public ArticleResponse(Long id, String title, String content, Long author_id, Long board_id, LocalDateTime created_date, LocalDateTime updated_date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author_id = author_id;
        this.board_id = board_id;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public Long getBoard_id() {
        return board_id;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public LocalDateTime getUpdated_date() {
        return updated_date;
    }

}