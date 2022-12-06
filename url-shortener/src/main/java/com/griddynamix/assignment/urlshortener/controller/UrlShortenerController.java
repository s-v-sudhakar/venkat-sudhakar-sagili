package com.griddynamix.assignment.urlshortener.controller;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.griddynamix.assignment.urlshortener.dto.OriginalUrl;
import com.griddynamix.assignment.urlshortener.dto.ShortenedUrl;
import com.griddynamix.assignment.urlshortener.service.UrlShortenerService;
import com.griddynamix.assignment.urlshortener.utils.UrlUtil;

@Controller
public class UrlShortenerController {

	Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);
	
	protected final UrlShortenerService service;
	
	@Autowired
	public UrlShortenerController(UrlShortenerService service) {
		this.service = service;
	}
	
	 /**
     * @param originalUrl Takes an object of OriginalUrl supplied in the request body
     * @return An object of ShortenedUrl serialized as JSON in the response
     */
	
	@PostMapping("/shortUrl")
	public ResponseEntity<?> createShortenedUrl(@RequestBody OriginalUrl originalUrl, HttpServletRequest request) {
		String baseUrl = null;
		
		try {
			baseUrl = UrlUtil.getBaseUrl(request.getRequestURL().toString());
		} catch (MalformedURLException e) {
			logger.error("Malformed request url");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request url is invalid", e);
		}
		
		ShortenedUrl shortUrl = service.getShortenedUrl(originalUrl);
		shortUrl.setShortenedUrl(baseUrl + shortUrl.getShortenedUrl());
		return new ResponseEntity<>(shortUrl,HttpStatus.OK);
	}
}
