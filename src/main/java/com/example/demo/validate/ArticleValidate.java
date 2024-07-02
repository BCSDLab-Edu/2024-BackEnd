package com.example.demo.validate;

import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.service.ArticleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleValidate {
    private final ArticleService articleService;

    ArticleValidate(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void validateResponseIsEmpty(List<ArticleResponse> response) {
        if (response.isEmpty())
            throw new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_ARTICLE);
    }

    public void validateContainId(Long id) {
        if (articleService.getAll().stream()
                .noneMatch(article -> article.getId().equals(id)))
            throw new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_ARTICLE);
    }

    public void validateExistAuthorId(Long id) {
        if (articleService.getAll().stream()
                .anyMatch(article -> article.getAuthor().getId().equals(id)))
            throw new ExceptionGenerator(StatusEnum.DELETE_MEMBER_PRESENT_ARTICLE);
    }
}
