package com.example.url_shortener.controller;

import com.example.url_shortener.model.ShortenRequest;
import com.example.url_shortener.model.ShortenResponse;
import com.example.url_shortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    /**
     * POST /shorten: Verkürzt eine URL
     */
    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody ShortenRequest request){
        if (request.getUrl() == null || request.getUrl().isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ShortenResponse(null, null, "Ungültige URL"));
        }
        String id = urlShortenerService.shortenUrl(request.getUrl());

        ShortenResponse response = new ShortenResponse(
                id,
                request.getUrl(),
                "http://localhost:8080/" + id
        );
        return ResponseEntity.ok(response);
    }

    /**
     * GET /{id}: Leitet zur Original-URL weiter
     */
    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable String id){
        String originalUrl = urlShortenerService.getOriginalUrl(id);
        if (originalUrl == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }
}
