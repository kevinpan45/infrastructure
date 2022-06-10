package io.kp45.config;


import org.springframework.cloud.client.loadbalancer.reactive.RetryableLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    @Bean
    public WebClient loadWebClient(final RetryableLoadBalancerExchangeFilterFunction function) {
        return WebClient.builder()
                .filter(function).build();
    }
}

