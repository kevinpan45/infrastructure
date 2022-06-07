package io.kp45.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {
    @Autowired
    ReactorLoadBalancerExchangeFilterFunction function;

    @Bean
    public WebClient loadWebClient() {
        return WebClient.builder()
                .filter(function).build();
    }
}

