package io.kp45.consul.kv;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import reactor.util.annotation.Nullable;

public class ConsulKVTemplate implements ConsulKVOperations {

    private final RestTemplate restTemplate;

    public ConsulKVTemplate(ConsulKVEndpoint endpoint) {
        this(endpoint, new SimpleClientHttpRequestFactory());
    }

    public ConsulKVTemplate(ConsulKVEndpoint endpoint, ClientHttpRequestFactory clientHttpRequestFactory) {
        this(SimpleConsulKVEndpointProvider.of(endpoint), clientHttpRequestFactory);
    }

    public ConsulKVTemplate(ConsulKVEndpointProvider endpointProvider, ClientHttpRequestFactory clientHttpRequestFactory) {
        this.restTemplate = doCreateRestTemplate(endpointProvider, clientHttpRequestFactory);
    }

    protected RestTemplate doCreateRestTemplate(ConsulKVEndpointProvider endpointProvider, ClientHttpRequestFactory requestFactory) {
        return ConsulKVTemplateBuilder.builder().endpointProvider(endpointProvider).requestFactory(requestFactory).build();
    }


    @Override
    public void write(String path, @Nullable Object body) {
        Assert.hasText(path, "Path must not be empty");
        restTemplate.put(path, body);
    }

    @Override
    public void delete(String path) {

    }

    @Override
    public Object read(String path) {
        return null;
    }

    @Override
    public <T> Object read(String path, Class<T> var2) {
        return null;
    }
}
