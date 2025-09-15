package com.example.urlshortener.controller;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.service.UrlShortenerService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    private final UrlShortenerService service;

    public UrlController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public UrlMapping shorten(@RequestParam String longUrl) {
        return service.shortenUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public String redirect(@PathVariable String shortUrl) {
        Optional<UrlMapping> mapping = service.getOriginalUrl(shortUrl);
        if (mapping.isPresent()) {
            UrlMapping urlMapping = mapping.get();
            urlMapping.setClickCount(urlMapping.getClickCount() + 1);
            return "Redirecting to: " + urlMapping.getLongUrl();
        }
        return "URL not found!";
    }
}
