package com.griddynamix.assignment.urlshortener.utils;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UrlUtilTest {
	
	@Test(expected = MalformedURLException.class)
	public void shouldThrowExceptionWhenMalformedUrlSuppliedWithoutProtocol() throws MalformedURLException {
		UrlUtil.getBaseUrl("malformed url dummy text");
	}
	
	@Test(expected = MalformedURLException.class)
	public void shouldThrowExceptionWhenMalformedUrlSuppliedWithIllegalChars() throws MalformedURLException {
		UrlUtil.getBaseUrl("malformed://test.com/foo");
	}
	
	@Test
	public void shouldReturnBaseUrlWhenValidUrlSuppliedWithoutPort() throws MalformedURLException {
		Assert.assertEquals(UrlUtil.getBaseUrl("http://test.com/hello"), "http://test.com/");
	}
	
	@Test
	public void shouldReturnBaseUrlWhenValidUrlSuppliedWithPort() throws MalformedURLException {
		Assert.assertEquals(UrlUtil.getBaseUrl("http://test.com:9090/hello"), "http://test.com:9090/");
	}
	
}
