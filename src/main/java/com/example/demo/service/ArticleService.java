package com.example.demo.service;

import java.util.List;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(
        ArticleRepository articleRepository,
        MemberRepository memberRepository,
        BoardRepository boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleResponse getById(Long id) {
        Article article = articleRepository.findById(id).get();
        return ArticleResponse.of(article);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = boardRepository.findById(boardId).get().getArticles();
        return articles.stream()
            .map(ArticleResponse::of)//인텔리제이가 이렇게 바꿔주더라구요.
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Member member = memberRepository.findById(request.authorId()).get();
        Board board = boardRepository.findById(request.boardId()).get();
        Article article = new Article(
            member,
            board,
            request.title(),
            request.description()
        );
        article.convenienceMethod(member, board);
        Article saved = articleRepository.insert(article);
        return ArticleResponse.of(saved);
    }

    public boolean isExist(Long id) {
        return articleRepository.isExist(id);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Member member = memberRepository.findById(request.authorId()).get();
        Board board = boardRepository.findById(request.boardId()).get();
        Article article = articleRepository.findById(id).get();
        article.update(member, board, request.title(), request.description());
        article.convenienceMethod(member, board);
        Article updated = articleRepository.update(article);
        return ArticleResponse.of(updated);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    public Boolean isExistBoard(Long boardid) {
        return boardRepository.isExist(boardid);
    }

    public Boolean isExistUser(Long userid) {
        return memberRepository.isExist(userid);
    }
}
