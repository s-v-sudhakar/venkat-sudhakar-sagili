package com.griddynamix.assignment.urlshortener.controller;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamix.assignment.urlshortener.dto.OriginalUrl;
import com.griddynamix.assignment.urlshortener.service.UrlShortenerService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UrlControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Test
    public void givenFullUrlReturnStatusOk() throws Exception {
        OriginalUrl originalUrl = new OriginalUrl("https://test.com/hello");

        mvc.perform(post("/shortUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(originalUrl)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenFullUrlReturnJsonWithShortUrlProp() throws Exception {
    	OriginalUrl originalUrlObj = new OriginalUrl("https://test.com/hello");

        mvc.perform(post("/shortUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(originalUrlObj)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenedUrl").exists());
    }

    @Test
    public void givenFullUrlReturnJsonWithShortUrlValueHasHttp() throws Exception {
    	OriginalUrl originalUrl = new OriginalUrl("https://test.com/hello");
        mvc.perform(post("/shortUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(originalUrl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenedUrl", startsWith("http")));
    }

    @Test
    public void shouldNotInsertFullUrlIfAlreadyExists() throws Exception {
    	OriginalUrl originalUrl = new OriginalUrl("https://test.com/hello");

        String shortUrl1 = mvc.perform(post("/shortUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(originalUrl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenedUrl", startsWith("http"))).andReturn().getResponse().getContentAsString();

        String shortUrl2 = mvc.perform(post("/shortUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(originalUrl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenedUrl", startsWith("http"))).andReturn().getResponse().getContentAsString();

        Assert.assertEquals(shortUrl1, shortUrl2);
    }

    @Test
    public void shouldNotInsertFullUrlIfDoesNotExist() throws Exception {
    	OriginalUrl originalUrl1 = new OriginalUrl("https://test.com/hello1");
    	OriginalUrl originalUrl2 = new OriginalUrl("https://test.com/hello2");

        String shortUrl1 = mvc.perform(post("/shortUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(originalUrl1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenedUrl", startsWith("http"))).andReturn().getResponse().getContentAsString();

        String shortUrl2 = mvc.perform(post("/shortUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(originalUrl2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenedUrl", startsWith("http"))).andReturn().getResponse().getContentAsString();

        Assert.assertNotEquals(shortUrl1, shortUrl2);
    }

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}