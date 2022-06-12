package io.kp45.consul.kv;


import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ConsulKVClients {
    public static RestTemplate createRestTemplate(ConsulKVEndpoint endpoint, ClientHttpRequestFactory requestFactory) {
        return createRestTemplate(SimpleConsulKVEndpointProvider.of(endpoint), requestFactory);
    }

    public static RestTemplate createRestTemplate(ConsulKVEndpointProvider endpointProvider, ClientHttpRequestFactory requestFactory) {
        RestTemplate restTemplate = createRestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setUriTemplateHandler(createUriBuilderFactory(endpointProvider));
        return restTemplate;
    }

    public static RestTemplate createRestTemplate() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList(3);
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    public static UriBuilderFactory createUriBuilderFactory(ConsulKVEndpointProvider endpointProvider) {
        return new PrefixAwareUriBuilderFactory(endpointProvider);
    }

    private static String toBaseUri(ConsulKVEndpoint endpoint) {
        return String.format("%s://%s:%s/%s", endpoint.getScheme(), endpoint.getHost(), endpoint.getPort(), endpoint.getPath());
    }

    static String prepareUriTemplate(@Nullable String baseUrl, String uriTemplate) {
        if (!uriTemplate.startsWith("http:") && !uriTemplate.startsWith("https:")) {
            if (baseUrl != null) {
                if (uriTemplate.startsWith("/") && baseUrl.endsWith("/")) {
                    return uriTemplate.substring(1);
                } else {
                    return !uriTemplate.startsWith("/") && !baseUrl.endsWith("/") ? "/" + uriTemplate : uriTemplate;
                }
            } else {
                try {
                    URI uri = URI.create(uriTemplate);
                    if (uri.getHost() != null) {
                        return uriTemplate;
                    }
                } catch (IllegalArgumentException var3) {
                }

                return !uriTemplate.startsWith("/") ? "/" + uriTemplate : uriTemplate;
            }
        } else {
            return uriTemplate;
        }
    }

    public static class PrefixAwareUriBuilderFactory extends DefaultUriBuilderFactory {
        private final ConsulKVEndpointProvider endpointProvider;

        public PrefixAwareUriBuilderFactory(ConsulKVEndpointProvider endpointProvider) {
            this.endpointProvider = endpointProvider;
        }

        public UriBuilder uriString(String uriTemplate) {
            if (!uriTemplate.startsWith("http:") && !uriTemplate.startsWith("https:")) {
                ConsulKVEndpoint endpoint = this.endpointProvider.getConsulKVEndpoint();
                String baseUri = ConsulKVClients.toBaseUri(endpoint);
                UriComponents uriComponents = UriComponentsBuilder.fromUriString(ConsulKVClients.prepareUriTemplate(baseUri, uriTemplate)).build();
                return UriComponentsBuilder.fromUriString(baseUri).uriComponents(uriComponents);
            } else {
                return UriComponentsBuilder.fromUriString(uriTemplate);
            }
        }
    }
}
