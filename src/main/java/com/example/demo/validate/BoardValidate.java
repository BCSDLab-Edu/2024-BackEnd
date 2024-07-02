package com.example.demo.validate;

import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.service.BoardService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardValidate {
    private final BoardService boardService;

    BoardValidate(BoardService boardService) {
        this.boardService = boardService;
    }

    public void validateCreateContainId(Long id) {
        if (boardService.getBoards().stream()
                .noneMatch(board -> board.id().equals(id)))
            throw new ExceptionGenerator(StatusEnum.CREATE_NOT_PRESENT_BOARD);
    }

    public void validateUpdateContainId(Long id) {
        if (boardService.getBoards().stream()
                .noneMatch(board -> board.id().equals(id)))
            throw new ExceptionGenerator(StatusEnum.EDIT_NOT_PRESENT_BOARD);
    }

    public void validateReadContainId(Long id) {
        if (boardService.getBoards().stream()
                .noneMatch(board -> board.id().equals(id)))
            throw new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_BOARD);
    }

    public void validateResponseIsEmpty(List<BoardResponse> response) {
        if (response.isEmpty())
            throw new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_BOARD);
    }
}
