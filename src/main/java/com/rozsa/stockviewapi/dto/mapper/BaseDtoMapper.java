package com.rozsa.stockviewapi.dto.mapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BaseDtoMapper<TModel, TDto> {
    TModel fromDto(TDto dto);

    List<TModel> fromDtos(List<TDto> dtos);

    default Mono<TModel> fromDtoToMono(TDto dto) {
        return Mono.just(fromDto(dto));
    }

    default Flux<TModel> fromDtosToFlux(List<TDto> dtos) {
        return Flux.fromIterable(fromDtos(dtos));
    }
}
