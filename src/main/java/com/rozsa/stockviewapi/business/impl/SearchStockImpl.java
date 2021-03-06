package com.rozsa.stockviewapi.business.impl;

import com.rozsa.stockviewapi.business.SearchStock;
import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import com.rozsa.stockviewapi.dto.mapper.StockSearchResultMapper;
import com.rozsa.stockviewapi.integration.service.StockDataService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Service
@ConditionalOnProperty(
        value="business.stock.search.flux",
        havingValue = "true",
        matchIfMissing = true)
public class SearchStockImpl implements SearchStock {
    private final StockDataService stockDataService;

    private final StockSearchResultMapper stockSearchResultMapper;

    public Flux<StockSearchResultDto> search(String query) {
        return stockDataService
                .searchFlux(query)
                .flatMap(stockSearchResultMapper::fromDtoToMono);
    }
}
