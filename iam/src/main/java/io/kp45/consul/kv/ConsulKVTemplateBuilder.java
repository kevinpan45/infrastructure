package io.kp45.consul.kv;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class ConsulKVTemplateBuilder {
    @Nullable
    private ConsulKVEndpointProvider endpointProvider;
    //TODO 超时配置及ssl配置
    private ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    @Nullable
    private ResponseErrorHandler errorHandler;

    private ConsulKVTemplateBuilder() {
    }

    public static ConsulKVTemplateBuilder builder() {
        return new ConsulKVTemplateBuilder();
    }

    public ConsulKVTemplateBuilder endpoint(ConsulKVEndpoint endpoint) {
        return this.endpointProvider(SimpleConsulKVEndpointProvider.of(endpoint));
    }

    public ConsulKVTemplateBuilder endpointProvider(ConsulKVEndpointProvider provider) {
        Assert.notNull(provider, "ConsulKVEndpointProvider must not be null");
        this.endpointProvider = provider;
        return this;
    }

    public ConsulKVTemplateBuilder requestFactory(ClientHttpRequestFactory requestFactory) {
        Assert.notNull(requestFactory, "ClientHttpRequestFactory must not be null");
        this.requestFactory = requestFactory;
        return this;
    }

    public ConsulKVTemplateBuilder errorHandler(ResponseErrorHandler errorHandler) {
        Assert.notNull(errorHandler, "ErrorHandler must not be null");
        this.errorHandler = errorHandler;
        return this;
    }

    public RestTemplate build() {
        Assert.state(this.endpointProvider != null, "ConsulKVEndpointProvider must not be null");
        RestTemplate restTemplate = this.createTemplate();
        if (this.errorHandler != null) {
            restTemplate.setErrorHandler(this.errorHandler);
        }
        return restTemplate;
    }

    protected RestTemplate createTemplate() {
        ClientHttpRequestFactory requestFactory = this.requestFactory;
        return ConsulKVClients.createRestTemplate(this.endpointProvider, requestFactory);
    }
}
