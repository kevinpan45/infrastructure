package io.kp45.filter.conver;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ServerAuthenticationConverter {

    Mono<String> convert(ServerWebExchange exchange);
}
