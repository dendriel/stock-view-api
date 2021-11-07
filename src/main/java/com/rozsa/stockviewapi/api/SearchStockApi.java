package com.rozsa.stockviewapi.api;

import com.rozsa.stockviewapi.business.SearchStock;
import com.rozsa.stockviewapi.configuration.CachedOperations;
import com.rozsa.stockviewapi.configuration.ReactiveRedisOperationsFactory;
import com.rozsa.stockviewapi.integration.service.dto.StockSearchResultServiceDto;
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
public class SearchStockApi {
    private final CachedOperations<StockSearchResultServiceDto> cachedOperations;
    private final SearchStock stockSearch;

    @GetMapping("/search")
    public Flux<StockSearchResultServiceDto> search(@RequestParam("query") String query) {
        return cachedOperations.getFlux(query, stockSearch::search);
    }

    @Bean
    static CachedOperations<StockSearchResultServiceDto> getStockSearchResultCachedOperations(ReactiveRedisOperationsFactory<StockSearchResultServiceDto> factory) {
        return CachedOperations.of(factory, StockSearchResultServiceDto.class);
    }
}
