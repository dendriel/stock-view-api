package com.rozsa.stockviewapi.service;

import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import reactor.core.publisher.Flux;

public interface StockDataService {
    Flux<StockSearchResultDto> search(String query);
}
