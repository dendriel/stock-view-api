package com.rozsa.stockviewapi.business;

import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import reactor.core.publisher.Flux;

public interface SearchStock {
    Flux<StockSearchResultDto> search(String query);
}
