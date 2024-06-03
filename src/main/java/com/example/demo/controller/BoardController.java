package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.response.MemberResponse;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public List<BoardResponse> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponse> getBoard(
        @PathVariable Long id
    ) {
        try {
            BoardResponse response = boardService.getBoardById(id);
            return ResponseEntity.ok(response);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/boards")
    public ResponseEntity<BoardResponse> createBoard(
        @RequestBody BoardCreateRequest request
    ) {
        if (boardService.isNullExist(request)) {
            return ResponseEntity.badRequest().build();
        }

        BoardResponse response = boardService.createBoard(request);
        return ResponseEntity.created(URI.create("/boards/" + response.id())).body(response);
    }

    @PutMapping("/boards/{id}")
    public BoardResponse updateBoard(
        @PathVariable Long id,
        @RequestBody BoardUpdateRequest updateRequest
    ) {
        return boardService.update(id, updateRequest);
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteBoard(
        @PathVariable Long id
    ) {
        if (boardService.isExistArticle(id)) {
            return ResponseEntity.badRequest().build();
        }
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
