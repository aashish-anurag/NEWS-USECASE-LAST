package com.news.newsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.newsservice.DTOs.EverythingDto;
import com.news.newsservice.DTOs.NewsResponse;
import com.news.newsservice.DTOs.TopHeadlinesRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class NewApiController {

    @GetMapping("/test")
    public String test(){
        return "Test news";
    }

    @Value("${api.key}")
    private String api_Key;

    @Value("${news.base.url}")
    private String baseUrl;

    @Value("${news.endpoint.everything}")
    private String endpoint_everything;

    @Value("${news.endpoint.topheadlines}")
    private String endpoint_topnews;



    @PostMapping(value = "/everything")
    public ResponseEntity<NewsResponse> getNewsArticles(@RequestBody EverythingDto everythingDto) throws IOException, InterruptedException, ParseException {
        log.info("Everything DTO ====> {}",everythingDto.toString());
        HttpResponse response = null;
        var queryParam = new ArrayList<String>();

        /**
         * q
         * Keywords or phrases to search for in the article title and body.
         *
         * Advanced search is supported here:
         *
         * Surround phrases with quotes (") for exact match.
         * Prepend words or phrases that must appear with a + symbol. Eg: +bitcoin
         * Prepend words that must not appear with a - symbol. Eg: -bitcoin
         * Alternatively you can use the AND / OR / NOT keywords, and optionally group these with parenthesis. Eg: crypto AND (ethereum OR litecoin) NOT bitcoin.
         * The complete value for q must be URL-encoded. Max length: 500 chars.
         */

        String q = everythingDto.getQ();
        if(Objects.nonNull(q) || q.length()==0){
            queryParam.add("q="+q);
        }

        //Default: all fields are searched.
        /**
         * The fields to restrict your q search to.
         * The possible options are:
         * title
         * description
         * content
         */
        String searchIn = everythingDto.getSearchIn();
        if(Objects.nonNull(searchIn)){
            queryParam.add("searchIn="+searchIn);
        }

        String sources = everythingDto.getSources();
        if(Objects.nonNull(sources)){
            long count = Arrays.stream(sources.split(",")).count();
            if(count<=20) {
                queryParam.add("sources=" + sources);
            }
        }

        String domains = everythingDto.getDomains();
        if(Objects.nonNull(domains)){
            queryParam.add("domains="+domains);
        }

        String excludeDomains=everythingDto.getExcludeDomains();
        if(Objects.nonNull(excludeDomains)){
            queryParam.add("excludeDomains="+excludeDomains);
        }

        String from = everythingDto.getFrom();
        //Default: the oldest according to your plan.
        if(Objects.nonNull(from)){
            queryParam.add("from="+from);
        }

        String to = everythingDto.getTo();
        //Default: the newest according to your plan.
        if(Objects.nonNull(to)){
            queryParam.add("to="+to);
        }

        String language = everythingDto.getLanguage();
        //Default: all languages returned.
        if(Objects.nonNull(language)){
            queryParam.add("language="+language);
        }

        String sortBy = everythingDto.getSortBy();
        //Default: publishedAt
        //order to sort==> relevancy, popularity, publishedAt
        if(Objects.nonNull(sortBy)){
            queryParam.add("sortBy="+sortBy);
        }

        String pageSize = everythingDto.getPageSize();
        //Default: 100. Maximum: 100.
        if(Objects.nonNull(pageSize)){
            try {
                if (Integer.parseInt(pageSize) > 0) {
                    queryParam.add("pageSize=" + pageSize);
                }
            }
            catch (NumberFormatException ex){
                log.info("value pf pageSize param is not in format ",ex.getMessage());
            }
        }

        String page = everythingDto.getPage();
        //Default: 1.
        if(Objects.nonNull(page)) {
            try {
                if (Integer.parseInt(page) > 0) {
                    queryParam.add("page=" + page);
                }
            }catch(NumberFormatException ex){
                log.info("value of page param is not in format",ex.getMessage());
            }
        }


        var final_queryParam = baseUrl+endpoint_everything+"?"+String.join("&", queryParam);
        log.info("final queryParam {}",final_queryParam);

        HttpClient client = HttpClient.newBuilder().
                connectTimeout(Duration.ofSeconds(30))
                .build();

        URI uri = URI.create(final_queryParam);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).setHeader("X-Api-Key",api_Key).build();
        response = client.send(request,HttpResponse.BodyHandlers.ofString());

        log.info("status code====>{}",response.statusCode());
        log.info("Body======>{}",response.body());

        String responseString = response.body().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        NewsResponse newsResponse = objectMapper.readValue(responseString, NewsResponse.class);
        return ResponseEntity.ok(newsResponse);
    }

    // TOPHEADLINE METHOD START

    @GetMapping("/topheadlines")
    public ResponseEntity<NewsResponse> getTopHeadlinesBrakingNews(@RequestBody TopHeadlinesRequestDto dto,
                                                                   @RequestParam(value="pageSize",defaultValue = "20") String pageSize,
                                                                   @RequestParam(value="page",defaultValue = "1") String page) throws IOException, InterruptedException {
        log.info("====TopHeadlinesRequestDto ====> {}",dto.toString());
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
        qParamTopNews.add("page="+dto.getPage());
        var final_qryPrmTopnews = baseUrl+endpoint_topnews+"?"+String.join("&", qParamTopNews);
        log.info("final queryParam {}",final_qryPrmTopnews);

        HttpClient client = HttpClient.newBuilder().
                connectTimeout(Duration.ofSeconds(30))
                .build();

        URI uri = URI.create(final_qryPrmTopnews);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).setHeader("X-Api-Key",api_Key).build();
        response = client.send(request,HttpResponse.BodyHandlers.ofString());

        log.info("Top news status code====>{}",response.statusCode());
        log.info("Body======>{}",response.body());

        String responseString = response.body().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        NewsResponse newsResponse = objectMapper.readValue(responseString, NewsResponse.class);
        return ResponseEntity.ok(newsResponse);

    }





}
