package com.rozsa.stockviewapi.service.impl;

import com.rozsa.stockviewapi.dto.StockPriceDto;
import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import com.rozsa.stockviewapi.service.StockDataService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class StatusInvestStockDataService implements StockDataService {

    private final WebClient client;

    public StatusInvestStockDataService(
            @Qualifier("status-invest") WebClient client
    ) {
        this.client = client;
    }

    public Flux<StockSearchResultDto> search(String query) {
        return client.get()
                .uri(String.format("/home/mainsearchquery?q=%s", query))
                .retrieve()
                .bodyToFlux(StockSearchResultDto.class);
    }

    public Flux<StockPriceDto> getPrices(String ticker) {
        return client.get()
                .uri(String.format("/acao/tickerprice?ticker=%s&type=0&currences[]=1", ticker))
                .retrieve()
                .bodyToFlux(StockPriceDto.class);
    }
}
