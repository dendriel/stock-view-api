package com.rozsa.stockviewapi.integration.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StockIndicatorsServiceDto {
    private Boolean success;
    private Map<String, List<Indicator>> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Indicator {
        private String key;
        private Double actual;
        private Double avg;
        private Double avgDifference;
        private Double minValue;
        private Double minValueRank;
        private Double maxValue;
        private Double maxValueRank;
        private String actual_F;
        private String avg_F;
        private String avgDifference_F;
        private String minValue_F;
        private Integer minValueRank_F;
        private String maxValue_F;
        private Integer maxValueRank_F;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Rank {
            private Integer timeType;
            private Integer rank;
            private String rank_F;
            private Double value;
            private String value_F;
            private Integer rankN;
        }
    }
}
