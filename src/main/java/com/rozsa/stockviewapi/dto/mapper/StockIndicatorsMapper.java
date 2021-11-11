package com.rozsa.stockviewapi.dto.mapper;

import com.rozsa.stockviewapi.dto.StockIndicatorsDto;
import com.rozsa.stockviewapi.integration.service.dto.StockIndicatorsServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class StockIndicatorsMapper {

    public Mono<StockIndicatorsDto> from(StockIndicatorsServiceDto serviceDto) {
        StockIndicatorsDto toDto = new StockIndicatorsDto();

        Map<String, List<StockIndicatorsServiceDto.Indicator>> data = serviceDto.getData();
        if (data == null || data.size() == 0) {
            log.warn("Data from stock indicators service DTO is empty! Success? {}", serviceDto.getSuccess());
            // TODO: return Mono.error()
            return Mono.just(toDto);
        }

        data.forEach((key, value) -> {
                    toDto.setTicker(key);
                    value
                        .forEach(indicator -> toDto.addIndicator(indicator.getKey(), indicator.getActual()));
                }
        );

        return Mono.just(toDto);
    }
}
