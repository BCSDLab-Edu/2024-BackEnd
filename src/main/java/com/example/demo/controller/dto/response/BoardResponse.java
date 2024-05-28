package com.example.demo.controller.dto.response;

import com.example.demo.domain.Board;

public record BoardResponse(
    Long id,
    String name
) {

    public static BoardResponse from(Board board) {
        return new BoardResponse(board.getId(), board.getName());
    }
}
