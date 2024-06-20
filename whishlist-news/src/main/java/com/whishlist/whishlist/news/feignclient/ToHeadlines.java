/*
package com.whishlist.whishlist.news.feignclient;


import com.whishlist.whishlist.news.dto.NewsResponse;
import com.whishlist.whishlist.news.dto.TopHeadlinesRequestDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name="NEWSAPP", url = "http://localhost:8011/wishlist/getHeadline")
@FeignClient(value="newsapp")
public interface ToHeadlines {
    @GetMapping("/getHeadline")
    //@Headers(value = "apiKey")
    public ResponseEntity<?> getHeadline(@RequestParam(value="country",required = false) String country,
                                    @RequestParam(value="category",required = false) String category,
                                    @RequestParam(value="sources",required = false) String sources,
                                    @RequestParam(value="q",required = false) String q,
                                    @RequestParam(value="pageSize",defaultValue = "20") String pageSize,
                                    @RequestParam(value="page",defaultValue = "1") String page,
                                    @RequestHeader("X-Api-Key") String apiKey);

}
*/
