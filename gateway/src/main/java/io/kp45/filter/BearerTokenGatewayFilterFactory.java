package io.kp45.filter;


import com.nimbusds.jose.jwk.JWKSet;
import io.kp45.filter.conver.BearerTokenAuthenticationConverter;
import io.kp45.filter.conver.ServerAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Component
@RefreshScope
@RequiredArgsConstructor
@Slf4j
public class BearerTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Value("${jwks:}")
    private String key;
    private static final String REFRESH_TOKEN_VALUE = "refreshToken";
    private ServerAuthenticationConverter serverAuthenticationConverter = new BearerTokenAuthenticationConverter();
    private final ReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder
            .withJwkSource((jwt) -> Flux.fromIterable(parseJWKSet().getKeys()))
            .build();

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            return this.serverAuthenticationConverter.convert(exchange)
                    .flatMap(jwtDecoder::decode)
                    .flatMap(jwt -> this.onAuthenticationSuccess(jwt, chain, exchange))
                    .onErrorResume(JwtValidationException.class,
                            (ex) -> Mono.defer(() -> {
                                exchange.getAttributes().put(REFRESH_TOKEN_VALUE, true);
                                return chain.filter(exchange);
                            }))
                    .onErrorResume(JwtException.class,
                            (ex) -> this.onAuthenticationFailure(exchange))
                    .onErrorResume(AuthenticationException.class,
                            (ex) -> this.onAuthenticationFailure(exchange));
        };
    }

    protected Mono<Void> onAuthenticationSuccess(Jwt jwt, GatewayFilterChain chain, ServerWebExchange exchange) {
        return Mono.defer(() -> {
            exchange.mutate().request((r) -> {
                r.headers((headers) -> {
                    headers.setBearerAuth(jwt.getTokenValue());
                });
            }).build();
            return chain.filter(exchange);
        });
    }


    protected Mono<Void> onAuthenticationFailure(ServerWebExchange exchange) {
        return Mono.defer(() -> {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(status);
            return response.setComplete();
        });
    }

    private JWKSet parseJWKSet() {
        try {
            return JWKSet.parse(this.key);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
