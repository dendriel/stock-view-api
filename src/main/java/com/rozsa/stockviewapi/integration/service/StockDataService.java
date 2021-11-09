package com.rozsa.stockviewapi.integration.service;

import com.rozsa.stockviewapi.integration.service.dto.StockPriceServiceDto;
import com.rozsa.stockviewapi.integration.service.dto.StockSearchResultServiceDto;
import com.rozsa.stockviewapi.integration.service.dto.StockIndicatorsServiceDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockDataService {
    /**
     * Search stocks by name.
     * @param query stock name.
     * @return search results.
     */
    Mono<StockSearchResultServiceDto> search(String query);

    /**
     * Get stock price information from previous days.
     * @param ticker target stock ticker.
     * @return stock prices from previous days.
     */
    Flux<StockPriceServiceDto> getPrices(String ticker);

    /**
     * Get stock price indicators history.
     * @param ticker target stock ticker.
     * @return stock price indicators (JSON text).
     */
    Mono<StockIndicatorsServiceDto> getIndicators(final String ticker);
}
