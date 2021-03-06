package com.rozsa.stockviewapi.api;

import com.rozsa.stockviewapi.business.SearchStock;
import com.rozsa.stockviewapi.cache.CachedOperations;
import com.rozsa.stockviewapi.cache.ReactiveRedisOperationsFactory;
import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.rozsa.stockviewapi.cache.CacheIndex.STOCK_SEARCH_RESULTS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class SearchStockApi {
    private final CachedOperations<StockSearchResultDto> cachedOperations;
    private final SearchStock stockSearch;

    @GetMapping("/search")
    public Flux<StockSearchResultDto> search(@RequestParam("query") String query) {
        return cachedOperations.getFlux(query, stockSearch::search);
    }

    @Bean
    static CachedOperations<StockSearchResultDto> getStockSearchResultCachedOperations(ReactiveRedisOperationsFactory<StockSearchResultDto> factory) {
        return CachedOperations.of(factory, StockSearchResultDto.class, STOCK_SEARCH_RESULTS.name());
    }
}
