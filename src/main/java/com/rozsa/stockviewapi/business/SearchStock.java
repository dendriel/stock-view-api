package com.rozsa.stockviewapi.business;

import com.rozsa.stockviewapi.integration.service.dto.StockSearchResultServiceDto;
import reactor.core.publisher.Flux;

public interface SearchStock {
    Flux<StockSearchResultServiceDto> search(String query);
}
