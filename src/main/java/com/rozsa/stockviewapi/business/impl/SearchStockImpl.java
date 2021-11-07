package com.rozsa.stockviewapi.business.impl;

import com.rozsa.stockviewapi.business.SearchStock;
import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import com.rozsa.stockviewapi.integration.service.StockDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Service
public class SearchStockImpl implements SearchStock {

    private final StockDataService stockDataService;

    public Flux<StockSearchResultDto> search(String query) {
        return stockDataService.search(query);
    }
}
