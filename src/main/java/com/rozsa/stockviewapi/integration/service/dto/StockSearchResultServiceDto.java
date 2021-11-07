package com.rozsa.stockviewapi.integration.service.dto;

import com.rozsa.stockviewapi.parser.NumberParser;
import lombok.Data;

@Data
public class StockSearchResultServiceDto {
    private Long id;
    private Long parentId;
    private String nameFormated;
    private String name;
    private String code;
    private Double price;
    private Double variation;
    private boolean variationUp;
    private Integer type;
    private String url;

    public void setPrice(String price) {
        this.price = NumberParser.parseCommaSeparatedDouble(price);
    }

    public void setVariation(String variation) {
        this.variation = NumberParser.parseCommaSeparatedDouble(variation);
    }
}

