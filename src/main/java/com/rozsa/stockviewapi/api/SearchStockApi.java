package com.rozsa.stockviewapi.api;

import com.rozsa.stockviewapi.business.SearchStock;
import com.rozsa.stockviewapi.dto.StockSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class SearchStockApi {

    private final SearchStock stockSearch;

    @GetMapping("/search")
    public Flux<StockSearchResultDto> search(@RequestParam("query") String query) {
        return stockSearch.search(query);
//        return Flux.just(
//                new StockSearchResultDto(650L, 480L, "TAEE11 - TAESA", "TAESA1", "TAE11", 36.8, 0.38, true, 1, "acoes/taee11"),
//                new StockSearchResultDto(651L, 480L, "TAEE11 - TAESA", "TAESA2", "TAE11", 36.8, 0.38, true, 1, "acoes/taee11"),
//                new StockSearchResultDto(652L, 480L, "TAEE11 - TAESA", "TAESA3", "TAE11", 36.8, 0.38, true, 1, "acoes/taee11"),
//                new StockSearchResultDto(653L, 480L, "TAEE11 - TAESA", "TAESA4", "TAE11", 36.8, 0.38, true, 1, "acoes/taee11"),
//                new StockSearchResultDto(654L, 480L, "TAEE11 - TAESA", "TAESA5", "TAE11", 36.8, 0.38, true, 1, "acoes/taee11")
//        );
    }
}
