package io.github.kevinpan45.common;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@ComponentScan
@Slf4j
public class PackageScanner {
    @PostConstruct
    public void initLog() {
        log.info("KP45 Tech Inc. MicroService Basic Framework Loaded");
    }
}
