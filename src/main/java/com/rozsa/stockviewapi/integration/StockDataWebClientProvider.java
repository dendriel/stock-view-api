package com.rozsa.stockviewapi.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class StockDataWebClientProvider {

    private static final String HTTP_HEADERS_AUTHORITY = "Authority";

    @Bean("status-invest")
    public WebClient create(
            @Value("${integration.stock.url}") String url,
            @Value("${integration.stock.authority}") String authority,
            @Value("${integration.stock.referer}") String referer,
            @Value("${integration.stock.user-agent}") String userAgent
    ) {
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HTTP_HEADERS_AUTHORITY, authority)
                .defaultHeader(HttpHeaders.REFERER, referer)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
