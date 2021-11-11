package com.rozsa.stockviewapi.dto;

import lombok.Data;

@Data
public class StockSearchResultDto {
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
}

