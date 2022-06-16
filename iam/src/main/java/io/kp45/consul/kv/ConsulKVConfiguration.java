package io.kp45.consul.kv;


import org.springframework.cloud.consul.ConsulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(ConsulConfiguredCondition.class)
public class ConsulKVConfiguration {

    @Bean
    public ConsulKVTemplate vaultTemplate(ConsulProperties properties) {
        ConsulKVEndpoint consulKVEndpoint = new ConsulKVEndpoint();
        consulKVEndpoint.setHost(properties.getHost());
        consulKVEndpoint.setScheme(properties.getScheme());
        consulKVEndpoint.setPort(properties.getPort());
        return new ConsulKVTemplate(consulKVEndpoint);
    }
}
