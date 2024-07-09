package com.example.demo.controller;

import java.util.List;

import com.example.demo.validate.ArticleValidate;
import com.example.demo.validate.BoardValidate;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.request.BoardCreateRequest;
import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.service.BoardService;

@RestController
public class BoardController {
    private final BoardService boardService;
    private final BoardValidate boardValidate;
    private final ArticleValidate articleValidate;

    public BoardController(BoardService boardService,
                           BoardValidate boardValidate,
                           ArticleValidate articleValidate) {
        this.boardService = boardService;
        this.boardValidate = boardValidate;
        this.articleValidate = articleValidate;
    }

    @GetMapping("/boards")
    public List<BoardResponse> getBoards() {
        List<BoardResponse> response = boardService.getBoards();

        boardValidate.validateResponseIsEmpty(response);

        return response;
    }

    @GetMapping("/boards/{id}")
    public BoardResponse getBoard(
            @PathVariable Long id
    ) {
        boardValidate.validateReadContainId(id);

        return boardService.getBoardById(id);
    }

    @PostMapping("/boards")
    public BoardResponse createBoard(
            @Valid @RequestBody BoardCreateRequest request
    ) {
        return boardService.createBoard(request);
    }

    @PutMapping("/boards/{id}")
    public BoardResponse updateBoard(
            @PathVariable Long id,
            @Valid @RequestBody BoardUpdateRequest updateRequest
    ) {
        return boardService.update(id, updateRequest);
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long id
    ) {
        boardService.deleteBoard(id);

        return ResponseEntity.noContent().build();
    }
}
