package com.griddynamix.assignment.urlshortener.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BaseConversionUtilTest {
	
	@Test
	public void shouldConvertMaxLongToShortString() {
		String maxIdShortString = BaseConversionUtil.idToEncodedString(Long.MAX_VALUE);
		Assert.assertNotNull(maxIdShortString);
		Assert.assertNotEquals(maxIdShortString,"");
	}
	
	@Test
	public void shouldThrowExceptionWhenShortStrLongerThanTenChars() {
		long id = BaseConversionUtil.encodedStringToId("asdndbaHAS5y");
	}
	
}
