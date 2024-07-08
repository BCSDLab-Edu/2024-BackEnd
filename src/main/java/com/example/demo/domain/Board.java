package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<Article> articles = new ArrayList<>();

    public void update(String name) {
        this.name = name;
    }

    public void addArticle(Article article) {
        if (articles == null) articles = new ArrayList<>();
        articles.add(article);
    }

    public Long getId() {
        return this.id = id;
    }

    public String getName() {
        return this.name = name;
    }
}
