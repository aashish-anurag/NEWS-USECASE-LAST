package com.whishlist.whishlist.news;

import com.whishlist.whishlist.news.dto.NewsResponse;
import com.whishlist.whishlist.news.dto.TopHeadlinesRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
@EnableFeignClients
public class WhishlistNewsApplication {
	/*@Autowired
	private static ToHeadlines headlines;*/

	public static void main(String[] args) {

		SpringApplication.run(WhishlistNewsApplication.class, args);

		/*TopHeadlinesRequestDto dto = new TopHeadlinesRequestDto();
		dto.setQ("");
		dto.setCategory("business");
		dto.setCountry("us");
		dto.setSources("");

		ResponseEntity<NewsResponse> response = headlines.getTopHeadlinesBrakingNews(dto,20+"",1+"");
		System.out.println(response);
*/

	}

}
