package com.rozsa.stockviewapi.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Data
public class StockIndicatorsDto {
    private String ticker;
    private Map<String, Object> indicators;

    public StockIndicatorsDto() {
        this.indicators = new HashMap<>();
    }

    public void addIndicator(String key, Object value) {
        indicators.put(key, value);
    }

    public void setTicker(String ticker) {
        this.ticker = ticker != null ? ticker.toUpperCase(Locale.ROOT) : null;
    }
}
