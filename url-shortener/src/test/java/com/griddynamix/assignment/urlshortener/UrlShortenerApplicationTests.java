package com.griddynamix.assignment.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.griddynamix.assignment.urlshortener.service.UrlShortenerService;

@SpringBootTest
class UrlShortenerApplicationTests {
	
	@Autowired
	private UrlShortenerService urlShortenerService;

	@Test
	void contextLoads() {
	}

}
