package com.rozsa.stockviewapi.dto.mapper;

import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import com.rozsa.stockviewapi.integration.service.dto.StockSearchResultServiceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockSearchResultMapper extends BaseDtoMapper<StockSearchResultDto, StockSearchResultServiceDto.Result> {
}
