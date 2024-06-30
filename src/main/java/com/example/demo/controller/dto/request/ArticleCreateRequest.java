package com.example.demo.controller.dto.request;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleCreateRequest {
    public Long board_id;
    public Long author_id;
    public String title;
    public String content;
}