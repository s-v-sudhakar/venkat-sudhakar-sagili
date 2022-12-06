package com.griddynamix.assignment.urlshortener.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.griddynamix.assignment.urlshortener.dto.OriginalUrl;
import com.griddynamix.assignment.urlshortener.dto.ShortenedUrl;
import com.griddynamix.assignment.urlshortener.model.UrlEntity;
import com.griddynamix.assignment.urlshortener.repository.UrlShortenerRepository;
import com.griddynamix.assignment.urlshortener.utils.BaseConversionUtil;

@Service
public class UrlShortenerService {
	
	Logger logger = LoggerFactory.getLogger(UrlShortenerService.class); 

	private final UrlShortenerRepository repository;

	@Autowired
	public UrlShortenerService(UrlShortenerRepository repository) {
		this.repository = repository;
	}

	/**
     * @param originalUrl OriginalUrl object to be converted to shortened URL
     * @return ShortenedUrl object
     */
	public ShortenedUrl getShortenedUrl(OriginalUrl originalUrl) {

		List<UrlEntity> savedUrls = null;
		savedUrls = checkOriginalUrlExists(originalUrl);

		UrlEntity savedUrl = null;
		if (savedUrls.isEmpty()) {
			logger.info(String.format("URL %s saved in database", originalUrl.getUrl()));
			savedUrl = this.save(originalUrl);
			logger.debug(savedUrl.toString());
		} else {
			savedUrl = savedUrls.get(0);
			logger.info(String.format("Insertion Skipped. URL: %s already exists in the database.", savedUrl));
		}

		logger.debug(String.format("Converting Base 10 %d to Base 62 string", savedUrl.getId()));
		String shortUrlText = BaseConversionUtil.idToEncodedString(savedUrl.getId());
		logger.info(String.format("Converted Base 10 %d to Base 62 string %s", savedUrl.getId(), shortUrlText));

        return new ShortenedUrl(shortUrlText);
	}

	private UrlEntity save(OriginalUrl originalUrl) {
		return repository.save(new UrlEntity(originalUrl.getUrl()));
	}

	/**
     * @param originalUrl
     * @return list of UrlEntity objects, list will be empty if no results found
     */
	private List<UrlEntity> checkOriginalUrlExists(OriginalUrl originalUrl) {
		return repository.findAllByOriginalUrl(originalUrl.getUrl());
	}
	
	/**
     * @param shortenedString Base62 encoded string
     * @return OriginalUrl object
     */
    public OriginalUrl getOriginalUrl(String shortenedString) {
        logger.debug("Converting Base 62 string %s to Base 10 id");
        Long id = BaseConversionUtil.encodedStringToId(shortenedString);
        logger.info(String.format("Converted Base 62 string %s to Base 10 id %s", shortenedString, id));

        logger.info(String.format("Retrieving full url for %d", id));
        return new OriginalUrl(this.get(id).getOriginalUrl());
    }
    
    private UrlEntity get(Long id) {
        logger.info(String.format("Fetching URL for Id %d", id));
        UrlEntity urlEntity = repository.findById(id).get();
        return urlEntity;
    }

}
