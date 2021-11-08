package com.rozsa.stockviewapi.business;

import com.rozsa.stockviewapi.dto.StockIndicatorsDto;
import reactor.core.publisher.Mono;

public interface GetStockIndicators {
    Mono<StockIndicatorsDto> getIndicators(String ticker);

}
