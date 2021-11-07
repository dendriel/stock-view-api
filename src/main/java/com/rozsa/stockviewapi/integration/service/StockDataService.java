package com.rozsa.stockviewapi.integration.service;

import com.rozsa.stockviewapi.dto.StockPriceDto;
import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import reactor.core.publisher.Flux;

public interface StockDataService {
    /**
     * Search stocks by name.
     * @param query stock name.
     * @return search results.
     */
    Flux<StockSearchResultDto> search(String query);

    /**
     * Get stock price information from previous days.
     * @param ticker target stock ticker.
     * @return stock prices from previous days.
     */
    Flux<StockPriceDto> getPrices(String ticker);
}
