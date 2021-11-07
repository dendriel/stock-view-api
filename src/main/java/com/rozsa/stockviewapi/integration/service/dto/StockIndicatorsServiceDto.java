package com.rozsa.stockviewapi.integration.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StockIndicatorsServiceDto {
    private Boolean success;
    private Map<String, Object> data;
}
