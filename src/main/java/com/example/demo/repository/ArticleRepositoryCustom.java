package com.example.demo.repository;

import com.example.demo.domain.Article;

public interface ArticleRepositoryCustom {

    Article saveAndRefresh(Article article);
}
