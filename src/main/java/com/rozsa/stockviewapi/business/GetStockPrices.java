package com.rozsa.stockviewapi.business;

import com.rozsa.stockviewapi.dto.StockPriceDto;
import reactor.core.publisher.Flux;

public interface GetStockPrices {
    Flux<StockPriceDto> getPrices(String ticker);
}
