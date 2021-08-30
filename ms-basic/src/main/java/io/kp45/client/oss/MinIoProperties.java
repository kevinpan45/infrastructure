package io.kp45.client.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinIoProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private int expires;
}
