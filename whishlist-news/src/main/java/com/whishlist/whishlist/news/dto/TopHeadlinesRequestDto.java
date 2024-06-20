package com.whishlist.whishlist.news.dto;

import lombok.Data;

@Data
public class TopHeadlinesRequestDto {
    public String country;
    public String category;
    public String sources;
    public String q;
    public String pageSize;
    public String page;
}
