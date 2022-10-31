package com.hyj.observer.config;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("executor")
class ExecutorsConfig {

    private int corePoolSize;

    private int maxPoolSize;

    private int queueCapacity;

    private int keepAliveSeconds;

    private String threadNamePrefix;

}

