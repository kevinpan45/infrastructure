package io.kp45.filter;

import io.kp45.filter.conver.RefreshTokenAuthenticationConverter;
import io.kp45.filter.conver.ServerAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RefreshTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private static final String REFRESH_TOKEN_VALUE = "refreshToken";
    private static String ACCESS_TOKEN_COOKIE_NAME = "ACCESS-TOKEN";
    private static String REFRESH_TOKEN_COOKIE_NAME = "REFRESH-TOKEN";
    private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<Map<String, Object>>() {
    };
    private BodyExtractor<Mono<Map<String, Object>>, ReactiveHttpInputMessage> bodyExtractor = new BodyExtractor<Mono<Map<String, Object>>, ReactiveHttpInputMessage>() {
        @Override
        public Mono<Map<String, Object>> extract(ReactiveHttpInputMessage inputMessage, Context context) {
            BodyExtractor<Mono<Map<String, Object>>, ReactiveHttpInputMessage> delegate = BodyExtractors
                    .toMono(STRING_OBJECT_MAP);
            return delegate.extract(inputMessage, context)
                    .onErrorMap((ex) -> new OAuth2AuthenticationException("An error occurred parsing the Access Token response: " + ex.getMessage()))
                    .switchIfEmpty(Mono.error(() -> new OAuth2AuthenticationException("Request access token response is empty")));

        }
    };
    private ServerAuthenticationConverter serverAuthenticationConverter = new RefreshTokenAuthenticationConverter();
    private final WebClient loadWebClient;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            if (exchange.getAttributeOrDefault(REFRESH_TOKEN_VALUE, false)) {
                return this.serverAuthenticationConverter.convert(exchange)
                        .flatMap((refreshTokenValue) -> this.loadWebClient.post()
                                .uri("http://iam-server/iam/oauth2/token")
                                .body(createTokenRequestBody(refreshTokenValue))
                                .exchange()
                                .flatMap((response) -> this.readTokenResponse(response))
                                .onErrorMap((ex) -> new OAuth2AuthenticationException(ex.getMessage())))
                        .flatMap((tokenResponse) -> this.onAuthenticationSuccess(tokenResponse, chain, exchange))
                        .onErrorResume(AuthenticationException.class, (ex) -> this.onAuthenticationFailure(exchange));
            }
            return chain.filter(exchange);
        };
    }

    protected Mono<Void> onAuthenticationSuccess(Map<String, Object> tokenResponse, GatewayFilterChain chain, ServerWebExchange exchange) {
        exchange.mutate().request((r) -> {
            r.headers((headers) -> {
                headers.setBearerAuth((String) tokenResponse.get("access_token"));
            });
        }).build();
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            this.refreshTokenCookies(exchange, tokenResponse);
        }));
    }

    protected Mono<Void> onAuthenticationFailure(ServerWebExchange exchange) {
        return Mono.defer(() -> {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(status);
            return response.setComplete();
        });
    }

    private BodyInserters.FormInserter<String> createTokenRequestBody(String refreshToken) {
        //TODO 更改client-id和client-secret
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(OAuth2ParameterNames.GRANT_TYPE, "refresh_token");
        parameters.add(OAuth2ParameterNames.CLIENT_ID, "relive-client");
        parameters.add(OAuth2ParameterNames.CLIENT_SECRET, "relive-client");
        parameters.add(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken);
        parameters.add(OAuth2ParameterNames.SCOPE, "profile");
        return BodyInserters.fromFormData(parameters);
    }

    private Mono<Map<String, Object>> readTokenResponse(ClientResponse response) {
        return response.body(this.bodyExtractor);
    }

    protected void refreshTokenCookies(ServerWebExchange exchange, Map<String, Object> tokenResponse) {
        MultiValueMap<String, ResponseCookie> cookies = exchange.getResponse().getCookies();
        cookies.add(ACCESS_TOKEN_COOKIE_NAME, ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, (String) tokenResponse.get("access_token"))
                .path("/")
                .secure(false)
                .httpOnly(true)
                .build());
        cookies.add(REFRESH_TOKEN_COOKIE_NAME, ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, (String) tokenResponse.get("refresh_token"))
                .path("/")
                .secure(false)
                .httpOnly(true)
                .build());
    }

}
