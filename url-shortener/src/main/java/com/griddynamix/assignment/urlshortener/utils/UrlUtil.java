package com.griddynamix.assignment.urlshortener.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtil {
	
	/**
     * @param url any valid URL
     * @return returns protocol://domain:port/ part of the input URL
     * @throws MalformedURLException
     */
	public static String getBaseUrl(String url) throws MalformedURLException {
        URL requestUrl = new URL(url);
        String protocol = requestUrl.getProtocol();
        String host = requestUrl.getHost();
        int port = requestUrl.getPort();

        if (port == -1) {
            return String.format("%s://%s/", protocol, host);
        } else {
            return String.format("%s://%s:%d/", protocol, host, port);
        }

    }

}
