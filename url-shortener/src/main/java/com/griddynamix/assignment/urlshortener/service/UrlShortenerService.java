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

	/**
	 * Instantiates a new url shortener service.
	 *
	 * @param repository the repository
	 */
	@Autowired
	public UrlShortenerService(UrlShortenerRepository repository) {
		this.repository = repository;
	}

	/**
	 * Gets the shortened url from the Original URL.
	 *
	 * @param originalUrl OriginalUrl object to be converted to shortened URL
	 * @return ShortenedUrl object
	 */
	public ShortenedUrl getShortenedUrl(OriginalUrl originalUrl) {

		List<UrlEntity> savedUrls = null;
		String shortUrlText = null;
		
		savedUrls = checkOriginalUrlExists(originalUrl);

		UrlEntity savedUrl = null;
		if (savedUrls.isEmpty()) {
			logger.info(String.format("URL %s saved in database", originalUrl.getUrl()));
			savedUrl = this.save(originalUrl);
			logger.debug(savedUrl.toString());
			logger.debug(String.format("Converting Base 10 %d to Base 62 string", savedUrl.getId()));
			shortUrlText = BaseConversionUtil.idToEncodedString(savedUrl.getId());
			savedUrl.setShortenedUrl(shortUrlText);
			this.persistShortUrlText(savedUrl);
			logger.info(String.format("Converted Base 10 %d to Base 62 string %s", savedUrl.getId(), shortUrlText));
		} else {
			savedUrl = savedUrls.get(0);
			logger.info(String.format("Insertion Skipped. URL: %s already exists in the database.", savedUrl));
			shortUrlText = savedUrl.getShortenedUrl();
		}

        return new ShortenedUrl(shortUrlText);
	}

	/**
	 * Save the Original URL in database.
	 *
	 * @param originalUrl the original url
	 * @return the url entity
	 */
	private UrlEntity save(OriginalUrl originalUrl) {
		return repository.save(new UrlEntity(originalUrl.getUrl()));
	}
	
	/**
	 * Persist short url text with respect to the saved UrlEntity.
	 *
	 * @param entity the entity
	 * @return the url entity
	 */
	private UrlEntity persistShortUrlText(UrlEntity entity) {
		return repository.save(entity);
	}

	/**
	 * Check if original url exists and if yes, return those entries.
	 *
	 * @param originalUrl the original url
	 * @return list of UrlEntity objects, list will be empty if no results found
	 */
	private List<UrlEntity> checkOriginalUrlExists(OriginalUrl originalUrl) {
		return repository.findAllByOriginalUrl(originalUrl.getUrl());
	}
	
	/**
	 * Gets the original url for the corresponding .
	 *
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
    
    /**
     * Gets the URL Entity from ID.
     *
     * @param id the id
     * @return the url entity
     */
    private UrlEntity get(Long id) {
        logger.info(String.format("Fetching URL for Id %d", id));
        UrlEntity urlEntity = repository.findById(id).get();
        return urlEntity;
    }

}
