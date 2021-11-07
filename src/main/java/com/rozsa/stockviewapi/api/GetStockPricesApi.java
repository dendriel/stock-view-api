package com.rozsa.stockviewapi.api;

import com.rozsa.stockviewapi.business.GetStockPrices;
import com.rozsa.stockviewapi.configuration.CachedOperations;
import com.rozsa.stockviewapi.configuration.ReactiveRedisOperationsFactory;
import com.rozsa.stockviewapi.dto.StockPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class GetStockPricesApi {
    private final CachedOperations<StockPriceDto> cachedOperations;
    private final GetStockPrices getStockPrice;

    @GetMapping("/prices")
    public Flux<StockPriceDto> get(@RequestParam("ticker") String ticker) {
        return cachedOperations.getFlux(ticker, getStockPrice::getPrices);
    }

    @Bean
    static CachedOperations<StockPriceDto> getStockPriceCachedOperations(ReactiveRedisOperationsFactory<StockPriceDto> factory) {
        return CachedOperations.of(factory, StockPriceDto.class);
    }
}
