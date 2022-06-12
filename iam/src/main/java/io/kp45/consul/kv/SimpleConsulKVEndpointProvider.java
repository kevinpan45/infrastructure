package io.kp45.consul.kv;


import org.springframework.util.Assert;

public class SimpleConsulKVEndpointProvider implements ConsulKVEndpointProvider {
    private final ConsulKVEndpoint endpoint;

    private SimpleConsulKVEndpointProvider(ConsulKVEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public static ConsulKVEndpointProvider of(ConsulKVEndpoint endpoint) {
        Assert.notNull(endpoint, "ConsulKVEndpoint must not be null");
        return new SimpleConsulKVEndpointProvider(endpoint);
    }


    @Override
    public ConsulKVEndpoint getConsulKVEndpoint() {
        return this.endpoint;
    }
}
