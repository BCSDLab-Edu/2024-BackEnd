package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import com.example.demo.controller.dto.response.ArticleResponse;
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
    public ResponseEntity getBoard(
        @PathVariable Long id
    ) {
        try {
            BoardResponse response = boardService.getBoardById(id);
            return ResponseEntity.ok(response);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾으려는 Board가 존재하지 않습니다.");
        }
    }

    @PostMapping("/boards")
    public ResponseEntity createBoard(
        @RequestBody BoardCreateRequest request
    ) {
        if (boardService.isNullExist(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null값은 허용되지 않습니다.");
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
    public ResponseEntity deleteBoard(
        @PathVariable Long id
    ) {
        if (boardService.isExistArticle(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 게시판은 작성한 글이 존재하므로 삭제할 수 없습니다.");
        }
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
