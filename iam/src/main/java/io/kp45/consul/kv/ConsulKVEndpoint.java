package io.kp45.consul.kv;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;

@Data
public class ConsulKVEndpoint implements Serializable {
    public static final String API_VERSION = "v1";
    private String host = "localhost";
    private int port = 8500;
    private String scheme = "https";
    private String path = "v1";

    public static ConsulKVEndpoint create(String host, int port) {
        Assert.hasText(host, "Host must not be empty");
        ConsulKVEndpoint consulKVEndpoint = new ConsulKVEndpoint();
        consulKVEndpoint.setHost(host);
        consulKVEndpoint.setPort(port);
        return consulKVEndpoint;
    }

    public static ConsulKVEndpoint from(URI uri) {
        Assert.notNull(uri, "URI must not be null");
        Assert.hasText(uri.getScheme(), "Schema must not be empty");
        Assert.hasText(uri.getHost(), "Host must not be empty");
        ConsulKVEndpoint consulKVEndpoint = new ConsulKVEndpoint();
        consulKVEndpoint.setHost(uri.getHost());

        try {
            consulKVEndpoint.setPort(uri.getPort() == -1 ? uri.toURL().getDefaultPort() : uri.getPort());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("Can't retrieve default port from %s", uri), e);
        }

        consulKVEndpoint.setScheme(uri.getScheme());
        String path = getPath(uri);
        if (StringUtils.hasText(path)) {
            consulKVEndpoint.setPath(path);
        }
        return consulKVEndpoint;
    }

    @Nullable
    private static String getPath(URI uri) {
        String path = uri.getPath();
        return path != null && path.startsWith("/") ? path.substring(1) : path;
    }

    public void setPort(int port) {
        Assert.isTrue(port >= 1 && port <= 65535, "Port must be a valid port in the range between 1 and 65535");
        this.port = port;
    }

    public void setScheme(String scheme) {
        Assert.isTrue("http".equals(scheme) || "https".equals(scheme), "Scheme must be http or https");
        this.scheme = scheme;
    }

    public void setPath(String path) {
        Assert.hasText(path, "Path must not be null or empty");
        Assert.isTrue(!path.startsWith("/"), () -> {
            return String.format("Path %s must not start with a leading slash", path);
        });
        this.path = path;
    }

    public URI createUri(String path) {
        return URI.create(this.createUriString(path));
    }

    public String createUriString(String path) {
        Assert.hasText(path, "Path must not be empty");
        return String.format("%s://%s:%s/%s/%s", this.getScheme(), this.getHost(), this.getPort(), this.getPath(), path);
    }
}
