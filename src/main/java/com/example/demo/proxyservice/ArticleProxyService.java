package com.example.demo.proxyservice;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.HTTPApiException;
import com.example.demo.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleProxyService {

    ArticleService articleservice;

    public ArticleProxyService(ArticleService articleservice) {
        this.articleservice = articleservice;
    }

    public List<ArticleResponse> getByBoardId(Long boardId) throws HTTPApiException {
        if(!articleservice.isExistBoard(boardId)) throw new HTTPApiException(ExceptionType.Board_IsNull);
        return articleservice.getByBoardId(boardId);
    }

    public ArticleResponse getById(Long id) {
        return articleservice.getById(id);
    }

    public ArticleResponse create(ArticleCreateRequest request) throws HTTPApiException {
        if(request.authorId()==null) throw new HTTPApiException(ExceptionType.Article_NotNullAuthorId);
        if(request.boardId()==null) throw new HTTPApiException(ExceptionType.Article_NotNullBoardId);
        if(request.title()==null) throw new HTTPApiException(ExceptionType.Article_NotNullTitle);
        if(request.description()==null) throw new HTTPApiException(ExceptionType.Article_NotNullDescription);
        if(!articleservice.isExistBoard(request.boardId())) throw new HTTPApiException(ExceptionType.Board_IsNull);
        if(!articleservice.isExistUser(request.authorId())) throw new HTTPApiException(ExceptionType.Member_IsNull);
        return articleservice.create(request);
    }

    public ArticleResponse update(Long id, ArticleUpdateRequest request) throws HTTPApiException {
        if(request.authorId()==null) throw new HTTPApiException(ExceptionType.Article_NotNullAuthorId);
        if(request.boardId()==null) throw new HTTPApiException(ExceptionType.Article_NotNullBoardId);
        if(request.title()==null) throw new HTTPApiException(ExceptionType.Article_NotNullTitle);
        if(request.description()==null) throw new HTTPApiException(ExceptionType.Article_NotNullDescription);
        if(!articleservice.isExistBoard(request.boardId())) throw new HTTPApiException(ExceptionType.Board_IsNull);
        if(!articleservice.isExistUser(request.authorId())) throw new HTTPApiException(ExceptionType.Member_IsNull);
        return articleservice.update(id, request);
    }

    public void delete(Long id) {
        articleservice.delete(id);
    }
}
