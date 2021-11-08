package com.rozsa.stockviewapi.business.impl;

import com.rozsa.stockviewapi.business.GetStockIndicators;
import com.rozsa.stockviewapi.dto.StockIndicatorsDto;
import com.rozsa.stockviewapi.dto.mapper.StockIndicatorsMapper;
import com.rozsa.stockviewapi.integration.service.StockDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class GetStockIndicatorsImpl implements GetStockIndicators {
    private final StockDataService stockDataService;

    private final StockIndicatorsMapper stockIndicatorsMapper;

    public Mono<StockIndicatorsDto> getIndicators(String ticker) {
        return stockDataService.getIndicators(ticker)
                .flatMap(stockIndicatorsMapper::from);
    }
}
