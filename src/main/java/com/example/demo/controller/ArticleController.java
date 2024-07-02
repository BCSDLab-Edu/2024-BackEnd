package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import com.example.demo.validate.ArticleValidate;
import com.example.demo.validate.BoardValidate;
import com.example.demo.validate.MemberValidate;
import jakarta.validation.Valid;
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
    private final ArticleValidate articleValidate;
    private final BoardValidate boardValidate;
    private final MemberValidate memberValidate;

    public ArticleController(ArticleService articleService,
                             ArticleValidate articleValidate,
                             BoardValidate boardValidate,
                             MemberValidate memberValidate) {
        this.articleService = articleService;
        this.articleValidate = articleValidate;
        this.boardValidate = boardValidate;
        this.memberValidate = memberValidate;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> getArticles(
            @RequestParam Long boardId
    ) {
        List<ArticleResponse> response = articleService.getByBoardId(boardId);

        articleValidate.validateResponseIsEmpty(response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticle(
            @PathVariable Long id
    ) {
        articleValidate.validateContainId(id);

        ArticleResponse response = articleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> crateArticle(
            @Valid @RequestBody ArticleCreateRequest request
    ) {
        boardValidate.validateCreateContainId(request.boardId());
        memberValidate.validateCreateContainId(request.authorId());

        ArticleResponse response = articleService.create(request);
        return ResponseEntity.created(URI.create("/articles/" + response.id())).body(response);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleUpdateRequest request
    ) {
        boardValidate.validateUpdateContainId(request.boardId());

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
