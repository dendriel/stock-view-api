package com.rozsa.stockviewapi.api;

import com.rozsa.stockviewapi.business.SearchStock;
import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class SearchStockApi {
    private final ReactiveRedisOperations<String, StockSearchResultDto> stockSearchResultOps;
    private final SearchStock stockSearch;

    @GetMapping("/search")
    public Flux<StockSearchResultDto> search(@RequestParam("query") String query) {
        return stockSearchResultOps
                .hasKey(query)
                .flatMapMany(exists -> exists ?
                    stockSearchResultOps.opsForList().range(query, 0, -1)
                    : stockSearch.search(query)
                        .flatMap(searchResult -> stockSearchResultOps.opsForList().leftPush(query, searchResult).map(res -> searchResult))
                );


    }
}
