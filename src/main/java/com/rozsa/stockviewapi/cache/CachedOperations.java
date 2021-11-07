package com.rozsa.stockviewapi.cache;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;


public final class CachedOperations<T> {
    private final ReactiveRedisOperations<String, T> redisOps;
    private final String keyPrefix;

    private CachedOperations(ReactiveRedisOperations<String, T> redisOps, String keyPrefix) {
        this.redisOps = redisOps;
        this.keyPrefix = keyPrefix;
    }

    public static <T> CachedOperations<T> of(ReactiveRedisOperationsFactory<T> factory, Class<T> clazz) {
        return of(factory, clazz, "");
    }

    public static <T> CachedOperations<T> of(ReactiveRedisOperationsFactory<T> factory, Class<T> clazz, String keyPrefix) {
        return new CachedOperations<>(factory.getOps(clazz), keyPrefix);
    }

    public String formatKey(String key) {
        return StringUtils.hasText(keyPrefix) ? String.format("%s_%s", keyPrefix, key) : key;
    }

    public Mono<T> getMono(String key, Function<String, Mono<T>> fetchFunc) {
        return getMono(key, () -> fetchFunc.apply(key));
    }

    public Mono<T> getMono(String key, Supplier<Mono<T>> fetchFunc) {
        final String formattedKey = formatKey(key);
        return redisOps.hasKey(formattedKey)
                .flatMap(exists -> exists ? loadCachedMono(formattedKey) : fetchAndSaveMono(formattedKey, fetchFunc));
    }

    private Mono<T> loadCachedMono(String formattedKey) {
        return redisOps.opsForValue()
                .get(formattedKey);
    }

    private Mono<T> fetchAndSaveMono(String formattedKey, Supplier<Mono<T>> fetchFunc) {
        return fetchFunc.get()
                .flatMap(result -> redisOps.opsForValue()
                        .set(formattedKey, result)
                        .thenReturn(result)
                );
    }

    public Flux<T> getFlux(String key, Function<String, Flux<T>> fetchFunc) {
        return getFlux(key, () -> fetchFunc.apply(key));
    }

    public Flux<T> getFlux(String key, Supplier<Flux<T>> fetchFunc) {
        final String formattedKey = formatKey(key);
        return redisOps.hasKey(formattedKey)
                .flatMapMany(exists -> exists ? loadCached(formattedKey) : fetchAndSave(formattedKey, fetchFunc));
    }

    private Flux<T> loadCached(String formattedKey) {
        return redisOps.opsForList()
                .range(formattedKey, 0, -1);
    }

    private Flux<T> fetchAndSave(String formattedKey, Supplier<Flux<T>> fetchFunc) {
        return fetchFunc.get()
                .flatMap(result -> redisOps.opsForList()
                        .leftPush(formattedKey, result)
                        .thenReturn(result)
                );
    }
}
