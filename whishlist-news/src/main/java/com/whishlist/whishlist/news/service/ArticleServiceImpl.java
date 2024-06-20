package com.whishlist.whishlist.news.service;

import com.whishlist.whishlist.news.entity.Article;
import com.whishlist.whishlist.news.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public Article saveArtcle(Article article) {
        return articleRepository.save(article);
    }
}
