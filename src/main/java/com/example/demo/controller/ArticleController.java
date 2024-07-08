package com.example.demo.controller;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> getArticles(@RequestParam(required = false) Long boardId) {
        List<ArticleResponse> response;
        if (boardId == null)
            response = articleService.getAll();
        else
            response = articleService.getByBoardId(boardId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Optional<Article>> getArticle(
            @PathVariable Long id
    ) {
        Optional<Article> response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(
            @RequestBody ArticleCreateRequest request
    ) {
        ArticleResponse response = articleService.create(request);
        return ResponseEntity.created(URI.create("/articles/" + response.getId())).body(response);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable Long id,
            @RequestBody ArticleUpdateRequest request
    ) {
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