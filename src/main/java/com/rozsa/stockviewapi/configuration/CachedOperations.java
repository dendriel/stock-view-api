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

    public Flux<T> getFlux(String key, Function<String, Flux<T>> fetchFunc) {
        return getFlux(key, () -> fetchFunc.apply(key));
    }

    public Flux<T> getFlux(String key, Supplier<Flux<T>> fetchFunc) {
        return redisOps
                .hasKey(key)
                .flatMapMany(exists -> exists ? loadCached(key) : fetchAndSave(key, fetchFunc));
    }

    private Flux<T> loadCached(String key) {
        return redisOps.opsForList()
                .range(key, 0, -1);
    }

    private Flux<T> fetchAndSave(String key, Supplier<Flux<T>> fetchFunc) {
        return fetchFunc.get()
                .flatMap(result -> redisOps.opsForList()
                        .leftPush(key, result)
                        .map(res -> result)
                );
    }
}
