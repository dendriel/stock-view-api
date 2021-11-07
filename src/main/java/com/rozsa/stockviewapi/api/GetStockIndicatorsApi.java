package com.rozsa.stockviewapi.api;

import com.rozsa.stockviewapi.business.GetStockIndicators;
import com.rozsa.stockviewapi.configuration.CachedOperations;
import com.rozsa.stockviewapi.configuration.ReactiveRedisOperationsFactory;
import com.rozsa.stockviewapi.integration.service.dto.StockIndicatorsServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class GetStockIndicatorsApi {
    private final CachedOperations<StockIndicatorsServiceDto> cachedOperations;
    private final GetStockIndicators getStockIndicators;

    @GetMapping("/indicators")
    public Mono<StockIndicatorsServiceDto> get(@RequestParam("ticker") String ticker) {
        return cachedOperations.getMono(ticker, getStockIndicators::getIndicators);
    }

    @Bean
    static CachedOperations<StockIndicatorsServiceDto> getStockIndicatorsCachedOperations(ReactiveRedisOperationsFactory<StockIndicatorsServiceDto> factory) {
        return CachedOperations.of(factory, StockIndicatorsServiceDto.class);
    }
}
