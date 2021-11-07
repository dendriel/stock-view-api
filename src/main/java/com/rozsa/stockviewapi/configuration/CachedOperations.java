package com.rozsa.stockviewapi.configuration;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.Supplier;


public final class CachedOperations<T> {
    private final ReactiveRedisOperations<String, T> redisOps;

    private CachedOperations(ReactiveRedisOperations<String, T> redisOps) {
        this.redisOps = redisOps;
    }

    public static <T> CachedOperations<T> of(ReactiveRedisOperationsFactory<T> factory, Class<T> clazz) {
        return new CachedOperations<T>(factory.getOps(clazz));
    }

    public Flux<T> getFlux(String key, Function<String, Flux<T>> retrieveFunc) {
        return getFlux(key, () -> retrieveFunc.apply(key));
    }

    public Flux<T> getFlux(String key, Supplier<Flux<T>> retrieveFunc) {
        return redisOps
                .hasKey(key)
                .flatMapMany(exists -> exists ?
                        redisOps.opsForList()
                                .range(key, 0, -1)
                        : retrieveFunc.get()
                        .flatMap(searchResult -> redisOps.opsForList()
                                .leftPush(key, searchResult)
                                .map(res -> searchResult)
                        )
                );
    }
}
