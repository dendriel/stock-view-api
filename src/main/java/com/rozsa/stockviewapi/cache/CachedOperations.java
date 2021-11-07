package com.rozsa.stockviewapi.cache;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;


public final class CachedOperations<T> {
    private final ReactiveRedisOperations<String, T> redisOps;

    private CachedOperations(ReactiveRedisOperations<String, T> redisOps) {
        this.redisOps = redisOps;
    }

    public static <T> CachedOperations<T> of(ReactiveRedisOperationsFactory<T> factory, Class<T> clazz) {
        return new CachedOperations<>(factory.getOps(clazz));
    }

    public Mono<T> getMono(String key, Function<String, Mono<T>> fetchFunc) {
        return getMono(key, () -> fetchFunc.apply(key));
    }

    public Mono<T> getMono(String key, Supplier<Mono<T>> fetchFunc) {
        return redisOps.hasKey(key)
                .flatMap(exists -> exists ? loadCachedMono(key) : fetchAndSaveMono(key, fetchFunc));
    }

    private Mono<T> loadCachedMono(String key) {
        return redisOps.opsForValue().get(key);
    }

    private Mono<T> fetchAndSaveMono(String key, Supplier<Mono<T>> fetchFunc) {
        return fetchFunc.get()
                .flatMap(result -> redisOps.opsForList()
                        .leftPush(key, result)
                        .thenReturn(result)
                );
    }

    public Flux<T> getFlux(String key, Function<String, Flux<T>> fetchFunc) {
        return getFlux(key, () -> fetchFunc.apply(key));
    }

    public Flux<T> getFlux(String key, Supplier<Flux<T>> fetchFunc) {
        return redisOps.hasKey(key)
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
                        .thenReturn(result)
                );
    }
}
