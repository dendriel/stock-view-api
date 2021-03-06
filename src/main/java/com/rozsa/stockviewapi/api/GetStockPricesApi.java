package com.rozsa.stockviewapi.api;

import com.rozsa.stockviewapi.business.GetStockPrices;
import com.rozsa.stockviewapi.cache.CachedOperations;
import com.rozsa.stockviewapi.cache.ReactiveRedisOperationsFactory;
import com.rozsa.stockviewapi.integration.service.dto.StockPriceServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.rozsa.stockviewapi.cache.CacheIndex.STOCK_PRICES;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class GetStockPricesApi {
    private final CachedOperations<StockPriceServiceDto> cachedOperations;
    private final GetStockPrices getStockPrice;

    @GetMapping("/prices")
    public Flux<StockPriceServiceDto> get(@RequestParam("ticker") String ticker) {
        return cachedOperations.getFlux(ticker, getStockPrice::getPrices);
    }

    @Bean
    static CachedOperations<StockPriceServiceDto> getStockPriceCachedOperations(ReactiveRedisOperationsFactory<StockPriceServiceDto> factory) {
        return CachedOperations.of(factory, StockPriceServiceDto.class, STOCK_PRICES.name());
    }
}
