package com.example.url_shortener.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UrlShortenerService {

    // In-Memory Speicher für Urls
    private final Map<String, String> urlMap = new HashMap<>();

    /**
     * Erstellt eine verkürzte URL
     * @param originalUrl Die Original-URL
     * @return Die generierte ID
     */
    public String shortenUrl (String originalUrl){
        String id = UUID.randomUUID().toString().substring(0, 8);

        urlMap.put(id, originalUrl);
        return id;
    }

    /**
     * Holt die Original-URL zu einer ID
     * @param id Die Kurz-ID
     * @return Die Original-URL oder null wenn nicht gefunden
     */
    public String getOriginalUrl(String id){
        return urlMap.get(id);
    }
}
