package com.griddynamix.assignment.urlshortener.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "url")
public class UrlEntity {
	
	private Long id;
	
	private String originalUrl;
	
	private String shortenedUrl;
	
	public UrlEntity() {
		
	}

	public UrlEntity(Long id, String originalUrl, String shortenedUrl) {
		this.id = id;
		this.originalUrl = originalUrl;
		this.shortenedUrl = shortenedUrl;
	}

	public UrlEntity(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getShortenedUrl() {
		return shortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}

	@Override
	public String toString() {
		return "UrlEntity [id=" + id + ", originalUrl=" + originalUrl + ", shortenedUrl=" + shortenedUrl + "]";
	}

}
