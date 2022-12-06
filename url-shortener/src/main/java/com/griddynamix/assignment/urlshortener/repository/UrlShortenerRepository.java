package com.griddynamix.assignment.urlshortener.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.griddynamix.assignment.urlshortener.model.UrlEntity;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlEntity, Long> {
	
	List<UrlEntity> findAllByOriginalUrl(String originalUrl);
}
