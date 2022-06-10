package io.kp45.filter.conver;


import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class RefreshTokenAuthenticationConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<String> convert(ServerWebExchange exchange) {
        return Mono.fromCallable(() -> token(exchange.getRequest())).map((token) -> {
            if (token.isEmpty()) {
                throw new OAuth2AuthenticationException("Not Found Refresh Token From Cookie");
            }
            return token;
        });
    }

    private String token(ServerHttpRequest request) {
        String refreshTokenFromCookie = resolveRefreshTokenFromCookie(request);
        if (refreshTokenFromCookie != null) {
            return refreshTokenFromCookie;
        }

        return null;
    }

    private static String resolveRefreshTokenFromCookie(ServerHttpRequest request) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (CollectionUtils.isEmpty(cookies)) {
            return null;
        }
        HttpCookie accessToken = cookies.getFirst("REFRESH-TOKEN");
        if (accessToken == null) {
            return null;
        }
        return accessToken.getValue();
    }
}
