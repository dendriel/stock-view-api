package com.rozsa.stockviewapi.business;

import com.rozsa.stockviewapi.integration.service.dto.StockIndicatorsServiceDto;
import reactor.core.publisher.Mono;

public interface GetStockIndicators {
    Mono<StockIndicatorsServiceDto> getIndicators(String ticker);

}
