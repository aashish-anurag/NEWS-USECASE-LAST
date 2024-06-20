package com.whishlist.whishlist.news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whishlist.whishlist.news.dto.ArticleDto;
import com.whishlist.whishlist.news.dto.NewsResponse;
import com.whishlist.whishlist.news.dto.SourceDto;
import com.whishlist.whishlist.news.dto.TopHeadlinesRequestDto;
import com.whishlist.whishlist.news.entity.Article;
import com.whishlist.whishlist.news.entity.Source;
import com.whishlist.whishlist.news.repository.ArticleRepository;
import com.whishlist.whishlist.news.repository.SourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/apiw/v1")
public class WishNewsController {

    @GetMapping("/test")
    public String test(){
        return "Wishlist";
    }

    @Value("${news.endpoint.topheadlines}")
    private String endpoint_topnews;


    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    SourceRepository sourceRepository;

  /*  @Autowired
    private ToHeadlines headlines;*/
/*
@RequestParam(value="q") String q,
                                                         @RequestParam(value="sources") String sources,
                                                         @RequestParam(value="country") String country,
                                                         @RequestParam(value="category") String category,
 */
    //@RequestBody TopHeadlinesRequestDto dto
    @GetMapping("/headline")
    public ResponseEntity<NewsResponse> getHeadlinesNews(@RequestBody TopHeadlinesRequestDto dto,
                                                         @RequestParam(value="pageSize",defaultValue = "20") String pageSize,
                                                         @RequestParam(value="page",defaultValue = "1") String page) throws IOException, InterruptedException {
        log.info("====WISHLIST PARAM ====> {}",dto.country);
        HttpResponse response = null;
        var qParamTopNews = new ArrayList<String>();
        //country code
        String country = dto.getCountry();
        if(Objects.nonNull(country)){
            qParamTopNews.add("country="+country);
        }

        //category param this should not mix with sources param
        String category = dto.getCategory();
        if(Objects.nonNull(category)){
            qParamTopNews.add("category="+category);
        }

        //sources param , we can't mix the value of country or category params
        String sources = dto.getSources();
        if(Objects.nonNull(sources)){
            qParamTopNews.add("sources="+sources);
        }

           /* default = 20 , max = 100 , either enter the pageSize in json
            else default value is set in @RequestParam

            String pageSize = dto.getPageSize();
          */
        int pSize=0;
        if(Objects.nonNull(pageSize)) {
            try {
                pSize = Integer.parseInt(pageSize);
            }catch(NumberFormatException ex){
                log.info("Trying to typecast from String type to number");
                ex.printStackTrace();
            }
            if (pSize >= 20 || pSize <= 100) {
                qParamTopNews.add("pageSize=" + pSize);
            }
        }
        // page , use this if totalResult > pageSize
        qParamTopNews.add("page="+page);
        var final_qryPrmTopnews = endpoint_topnews+"?"+String.join("&", qParamTopNews);
        log.info("final queryParam {}",final_qryPrmTopnews);
/*

        HttpClient client = HttpClient.newBuilder().
                connectTimeout(Duration.ofSeconds(30))
                .build();

*/
        URI uri = URI.create(final_qryPrmTopnews);
        java.net.http.HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
//        response = client.send(request,HttpResponse.BodyHandlers.ofString());

        RestTemplate restTemplate = new RestTemplate();
       NewsResponse newResponse = restTemplate.getForEntity(uri.toString(),NewsResponse.class).getBody();

        log.info("Top news status code====>{}",newResponse.getStatus());
        log.info("Body======>{}",newResponse.getArticles());

        String responseString = response.body().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        NewsResponse  newsResponse = objectMapper.readValue(newResponse.toString(), NewsResponse.class);
        log.info("response data= {}",newsResponse.toString());


    for (ArticleDto article : newsResponse.getArticles()) {
            SourceDto source = article.getSource();
            Source entitysource = new Source();
            entitysource.setId(Long.parseLong(source.getId()));
            entitysource.setName(source.getName());
            sourceRepository.save(entitysource);
            Article article1 = new Article();
            article1.setId(Long.parseLong(article.getId()));
            article1.setAuthor(article.getAuthor());
            article1.setContent(article.getContent());
            article1.setUrl(article.getUrl());
            article1.setDescription(article.getDescription());
            article1.setPublishedAt(article.getPublishedAt());
            article1.setUrlToImage(article.getUrlToImage());
            articleRepository.save(article1);
        }

        return ResponseEntity.ok(newsResponse);

    }

}
