package io.kp45.security.jose;


import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.kp45.consul.kv.ConsulKVTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ConsulConfigJWKSet implements JWKSource<SecurityContext>, InitializingBean {
    private String path = "/kv/config/apps/data";
    private final ConsulKVTemplate consulKVTemplate;
    private JWKSet jwkSet;

    public ConsulConfigJWKSet(final ConsulKVTemplate consulKVTemplate) {
        Assert.notNull(consulKVTemplate, "ConsulKVTemplate must not be null");
        this.consulKVTemplate = consulKVTemplate;
        this.init();
    }

    @Override
    public List<JWK> get(JWKSelector jwkSelector, SecurityContext securityContext) throws KeySourceException {
        return jwkSelector.select(this.jwkSet);
    }

    public void rotate() {
        init();
    }

    private void init() {
        RSAKey rsaKey = Jwks.generateRsa();
        this.jwkSet = new JWKSet(Arrays.asList(rsaKey));
        consulKVTemplate.write(path, Collections.singletonMap("jwks", jwkSet.toString()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    public void setPath(String path) {
        this.path = path;
    }
}
