package com.rozsa.stockviewapi.business.impl;

import com.rozsa.stockviewapi.business.GetStockIndicators;
import com.rozsa.stockviewapi.integration.service.StockDataService;
import com.rozsa.stockviewapi.integration.service.dto.StockIndicatorsServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class GetStockIndicatorsImpl implements GetStockIndicators {
    private final StockDataService stockDataService;

    public Mono<StockIndicatorsServiceDto> getIndicators(String ticker) {
        return stockDataService.getIndicators(ticker);
    }
}
