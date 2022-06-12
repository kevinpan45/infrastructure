package io.kp45.consul.kv;

@FunctionalInterface
public interface ConsulKVEndpointProvider {

    ConsulKVEndpoint getConsulKVEndpoint();
}
