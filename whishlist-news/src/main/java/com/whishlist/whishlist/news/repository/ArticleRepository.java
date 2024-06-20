package com.whishlist.whishlist.news.repository;

import com.whishlist.whishlist.news.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {

}
