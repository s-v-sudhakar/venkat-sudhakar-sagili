package com.griddynamix.assignment.urlshortener.utils;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.ResponseEntity;

import com.griddynamix.assignment.urlshortener.dto.OriginalUrl;
import com.griddynamix.assignment.urlshortener.err.InvalidUrlMessageError;

public class ValidationUtil {
	
	/**
	 * Validate url.
	 *
	 * @param originalUrl the original url
	 * @return returns InvalidUrlMessageError if URL is not valid
	 */
	
	public static ResponseEntity<InvalidUrlMessageError> validateUrl(OriginalUrl originalUrl) {
		UrlValidator validator = new UrlValidator(new String[] {"http","https"}, UrlValidator.ALLOW_LOCAL_URLS);
		
		String url = originalUrl.getUrl();
		
		if(!validator.isValid(url)) {
			
			InvalidUrlMessageError errorMsg = new InvalidUrlMessageError("url", originalUrl.getUrl(), "Invalid URL");
			
			return ResponseEntity.badRequest().body(errorMsg);
		}
		return null;
	}
	
	private ValidationUtil() {}

}
