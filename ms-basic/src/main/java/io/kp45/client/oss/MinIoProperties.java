package io.kp45.client.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "oss.minio")
public class MinIoProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private int expires;
}
