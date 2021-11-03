package com.rozsa.stockviewapi.business.impl;

import com.rozsa.stockviewapi.business.GetStockPrices;
import com.rozsa.stockviewapi.dto.StockPriceDto;
import com.rozsa.stockviewapi.service.StockDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class GetStockPriceImpl implements GetStockPrices {
    private final StockDataService stockDataService;

    public Flux<StockPriceDto> getPrices(String ticker) {
        return stockDataService.getPrices(ticker);
    }
}