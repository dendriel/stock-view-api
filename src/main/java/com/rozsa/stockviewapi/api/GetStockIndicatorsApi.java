package com.rozsa.stockviewapi.api;

import com.rozsa.stockviewapi.business.GetStockIndicators;
import com.rozsa.stockviewapi.cache.CachedOperations;
import com.rozsa.stockviewapi.cache.ReactiveRedisOperationsFactory;
import com.rozsa.stockviewapi.dto.StockIndicatorsDto;
import com.rozsa.stockviewapi.integration.service.dto.StockIndicatorsServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.rozsa.stockviewapi.cache.CacheIndex.STOCK_INDICATORS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class GetStockIndicatorsApi {
    private final CachedOperations<StockIndicatorsDto> cachedOperations;
    private final GetStockIndicators getStockIndicators;

    @GetMapping("/indicators")
    public Mono<StockIndicatorsDto> get(@RequestParam("ticker") String ticker) {
        return cachedOperations.getMono(ticker, getStockIndicators::getIndicators);
    }

    @Bean
    static CachedOperations<StockIndicatorsDto> getStockIndicatorsCachedOperations(ReactiveRedisOperationsFactory<StockIndicatorsDto> factory) {
        return CachedOperations.of(factory, StockIndicatorsDto.class, STOCK_INDICATORS.name());
    }
}
