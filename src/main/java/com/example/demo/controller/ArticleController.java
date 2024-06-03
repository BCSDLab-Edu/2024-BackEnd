package com.example.demo.controller;

import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.example.demo.controller.Error.ErrorCode;
import com.example.demo.controller.Error.ErrorResponse;
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
    public ResponseEntity<ArticleResponse> getArticle(
        @PathVariable Long id
    ) {
        ArticleResponse response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/articles")
    public ResponseEntity<?> createArticle(
        @RequestBody ArticleCreateRequest request
    ) {
        try {
            ArticleResponse response = articleService.create(request);
            return ResponseEntity.created(URI.create("/articles/" + response.id())).body(response);
        }catch (Exception SQLIntegrityConstraintViolationException){
            final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_EXIST_USER);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
        }
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<?> updateArticle(
        @PathVariable Long id,
        @RequestBody ArticleUpdateRequest request
    ) {
        try {
            ArticleResponse response = articleService.update(id, request);
            return ResponseEntity.ok(response);
        }catch (Exception DataIntegrityViolationException){
            System.out.println();
            final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_EXIST_USER);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
        }
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> updateArticle(
        @PathVariable Long id
    ) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
