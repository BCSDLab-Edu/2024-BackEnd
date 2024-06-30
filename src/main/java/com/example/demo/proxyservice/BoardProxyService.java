package com.example.demo.proxyservice;

import com.example.demo.controller.dto.request.BoardCreateRequest;
import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.HTTPApiException;
import com.example.demo.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardProxyService {

    BoardService boardservice;

    public BoardProxyService(BoardService boardservice) {
        this.boardservice = boardservice;
    }

    public List<BoardResponse> getBoards() {
        return boardservice.getBoards();
    }

    public BoardResponse getBoardById(Long id) {
        return boardservice.getBoardById(id);
    }

    public BoardResponse createBoard(BoardCreateRequest request) throws HTTPApiException {
        if(request.name()==null) throw new HTTPApiException(ExceptionType.Board_NotNullName);
        return boardservice.createBoard(request);
    }

    public BoardResponse update(Long id, BoardUpdateRequest updateRequest) throws HTTPApiException {
        if(updateRequest.name()==null) throw new HTTPApiException(ExceptionType.Board_NotNullName);
        return boardservice.update(id, updateRequest);
    }

    public void deleteBoard(Long id) throws HTTPApiException {
        try{
            boardservice.deleteBoard(id);
        }catch(Exception e) {
            throw new HTTPApiException(ExceptionType.Board_CantDelete);
        }
    }
}
