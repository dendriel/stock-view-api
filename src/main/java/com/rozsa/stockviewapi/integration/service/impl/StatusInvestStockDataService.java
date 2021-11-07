package com.rozsa.stockviewapi.integration.service.impl;

import com.rozsa.stockviewapi.integration.service.dto.StockPriceServiceDto;
import com.rozsa.stockviewapi.integration.service.dto.StockSearchResultServiceDto;
import com.rozsa.stockviewapi.integration.service.StockDataService;
import com.rozsa.stockviewapi.integration.service.dto.StockIndicatorsServiceDto;
import io.netty.handler.codec.http.HttpHeaderValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StatusInvestStockDataService implements StockDataService {
    private final WebClient client;

    public StatusInvestStockDataService(@Qualifier("status-invest") WebClient client) {
        this.client = client;
    }

    public Flux<StockSearchResultServiceDto> search(final String query) {
        return client.get()
                .uri(String.format("/home/mainsearchquery?q=%s", query))
                .retrieve()
                .bodyToFlux(StockSearchResultServiceDto.class);
    }

    public Flux<StockPriceServiceDto> getPrices(final String ticker) {
        return client.get()
                .uri(String.format("/acao/tickerprice?ticker=%s&type=0&currences[]=1", ticker))
                .retrieve()
                .bodyToFlux(StockPriceServiceDto.class);
    }

    public Mono<StockIndicatorsServiceDto> getIndicators(final String ticker) {
        return client.post()
                .uri("/acao/indicatorhistoricallist")
                .body(Mono.just(String.format("codes[]=%s&time=7&byQuarter=false", ticker)), String.class)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(StockIndicatorsServiceDto.class);
    }
}
