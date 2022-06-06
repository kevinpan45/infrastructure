package io.kp45.filter.conver;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BearerTokenAuthenticationConverter implements ServerAuthenticationConverter {
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);
    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    @Override
    public Mono<String> convert(ServerWebExchange exchange) {
        return Mono.fromCallable(() -> token(exchange.getRequest())).map((token) -> {
            if (token.isEmpty()) {
                throw new OAuth2AuthenticationException("Not Found Bearer Token");
            }
            return token;
        });
    }

    private String token(ServerHttpRequest request) {
        String authorizationHeaderToken = resolveFromAuthorizationHeader(request.getHeaders());
        String accessTokenFromCookie = resolveAccessTokenFromCookie(request);
        if (accessTokenFromCookie != null) {
            return accessTokenFromCookie;
        }
        if (authorizationHeaderToken != null) {
            return authorizationHeaderToken;
        }
        return null;
    }

    private static String resolveAccessTokenFromCookie(ServerHttpRequest request) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (CollectionUtils.isEmpty(cookies)) {
            return null;
        }
        HttpCookie accessToken = cookies.getFirst("ACCESS-TOKEN");
        if (accessToken == null) {
            return null;
        }
        return accessToken.getValue();
    }

    private String resolveFromAuthorizationHeader(HttpHeaders headers) {
        String authorization = headers.getFirst(this.bearerTokenHeaderName);
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            throw new OAuth2AuthenticationException("");
        }
        return matcher.group("token");
    }
}
