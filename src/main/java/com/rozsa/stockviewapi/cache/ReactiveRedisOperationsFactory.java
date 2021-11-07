package com.rozsa.stockviewapi.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@RequiredArgsConstructor
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class ReactiveRedisOperationsFactory<T> {

    private final ReactiveRedisConnectionFactory factory;

    public ReactiveRedisOperations<String, T> getOps(Class<T> clazz) {
        Jackson2JsonRedisSerializer<T> serializer = new Jackson2JsonRedisSerializer<>(clazz);

        RedisSerializationContext.RedisSerializationContextBuilder<String, T> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, T> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
