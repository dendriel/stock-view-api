package com.rozsa.stockviewapi.integration.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class StockPriceServiceDto {
    private String symbol;
    private List<Price> prices;

    @Data
    static class Price {
        private double price;
        private String date;
    }
}
