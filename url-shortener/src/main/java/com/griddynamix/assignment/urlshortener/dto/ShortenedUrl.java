package com.griddynamix.assignment.urlshortener.dto;

public class ShortenedUrl {
	
	private String shortenedUrl;

	public ShortenedUrl() {
		
	}
	
	public ShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}

	public String getShortenedUrl() {
		return shortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}
	
}
