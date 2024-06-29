package com.example.demo.controller.dto.response;

import com.example.demo.domain.Board;

public class BoardResponse {
    Long id;
    String name;

    public BoardResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
