package com.rozsa.stockviewapi.business;

import com.rozsa.stockviewapi.integration.service.dto.StockPriceServiceDto;
import reactor.core.publisher.Flux;

public interface GetStockPrices {
    Flux<StockPriceServiceDto> getPrices(String ticker);
}
