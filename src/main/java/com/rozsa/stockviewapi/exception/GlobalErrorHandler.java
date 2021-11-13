package com.rozsa.stockviewapi.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
@Order(-2)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        String errorMessage = "Unknown error";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (throwable instanceof HttpServerErrorException exception) {
            errorMessage = exception.getMessage() != null ? exception.getMessage() : errorMessage;
            httpStatus = exception.getStatusCode();
        }

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.setStatusCode(httpStatus);

        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(errorMessage.getBytes());

        return response.writeWith(Mono.just(dataBuffer));
    }
}
