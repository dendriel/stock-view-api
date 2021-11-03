package com.rozsa.stockviewapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class StockPriceDto {
    private String symbol;
    private List<Price> prices;

    @Data
    static class Price {
        private double price;
        private String date;
    }
}
