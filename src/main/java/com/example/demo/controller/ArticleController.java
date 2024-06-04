package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import com.example.demo.controller.dto.response.MemberResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.service.ArticleService;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> getArticles(
        @RequestParam Long boardId
    ) {
        List<ArticleResponse> response = articleService.getByBoardId(boardId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity getArticle(
        @PathVariable Long id
    ) {
        try {
            ArticleResponse response = articleService.getById(id);
            return ResponseEntity.ok(response);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾으려는 Article이 존재하지 않습니다.");
        }
    }

    @PostMapping("/articles")
    public ResponseEntity crateArticle(
        @RequestBody ArticleCreateRequest request
    ) {
        if (articleService.isNullExist(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null값은 허용되지 않습니다.");
        }

        if (articleService.isMemberAndBoardExist(request.boardId(), request.authorId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성자나 계시판이 존재하지 않습니다.");
        }

        ArticleResponse response = articleService.create(request);
        return ResponseEntity.created(URI.create("/articles/" + response.id())).body(response);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity updateArticle(
        @PathVariable Long id,
        @RequestBody ArticleUpdateRequest request

    ) {
        if (!articleService.isIdExist(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정하려는 게시글의 Id가 존재하지 않습니다.");
        }
        ArticleResponse response = articleService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> updateArticle(
        @PathVariable Long id
    ) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
