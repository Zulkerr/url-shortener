package com.example.url_shortener.controller;


import com.example.url_shortener.model.ShortenRequest;
import com.example.url_shortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlShortenerService urlShortenerService;

    /**
     * Test für POST /shorten - Erfolgreicher Fall
     */
    @Test
    void testShortenUrl_Success() throws Exception {
        // Arrange: Mock-Verhalten definieren
        when(urlShortenerService.shortenUrl(anyString())).thenReturn("abc12345");

        // Act & Assert: Request senden und Response prüfen
        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"https://www.example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc12345"))
                .andExpect(jsonPath("$.originalUrl").value("https://www.example.com"))
                .andExpect(jsonPath("$.shortUrl").value("http://localhost:8080/abc12345"));
    }

    /**
     * Test für POST /shorten - Leere URL (Bad Request)
     */
    @Test
    void testShortenUrl_EmptyUrl() throws Exception {
        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test für GET /{id} - URL existiert (Redirect)
     */
    @Test
    void testRedirectToUrl_Success() throws Exception {
        // Arrange
        when(urlShortenerService.getOriginalUrl("abc12345"))
                .thenReturn("https://www.example.com");

        // Act & Assert
        mockMvc.perform(get("/abc12345"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://www.example.com"));
    }

    /**
     * Test für GET /{id} - URL existiert nicht (404)
     */
    @Test
    void testRedirectToUrl_NotFound() throws Exception {
        // Arrange
        when(urlShortenerService.getOriginalUrl("notfound"))
                .thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/notfound"))
                .andExpect(status().isNotFound());
    }
}
