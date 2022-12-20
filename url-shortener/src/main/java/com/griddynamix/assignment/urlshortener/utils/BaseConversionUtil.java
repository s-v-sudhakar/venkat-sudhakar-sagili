package com.griddynamix.assignment.urlshortener.utils;


public class BaseConversionUtil {

	/**
     * a-z A-Z 0-9 characters. Each character appears only once in the string
     */
    public static final String ALLOWEDSTRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final int BASE = ALLOWEDSTRING.length();

    /**
     * Id to encoded string.
     *
     * @param num Base10 number of type Long
     * @return base62 encoded string representation of input Long
     */
    public static String idToEncodedString(Long num) {
        StringBuilder str = new StringBuilder();
        while (num > 0) {
            str.insert(0, ALLOWEDSTRING.charAt((int) (num % BASE)));
            num = num / BASE;
        }
        return str.toString();
    }

    /**
     * Encoded string to id.
     *
     * @param str Base62 encoded string
     * @return decoded Base10 number of type Long
     */
    public static Long encodedStringToId(String str) {
        Long num = 0L;
        for (int i = 0; i < str.length(); i++) {
            num = num * BASE + ALLOWEDSTRING.indexOf(str.charAt(i));
        }
        return num;
    }

    private BaseConversionUtil() {}

}
