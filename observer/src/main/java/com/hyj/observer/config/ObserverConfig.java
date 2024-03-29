package com.hyj.observer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ObserverConfig {

    @Autowired
    private ExecutorsConfig executorsConfig;

    @Bean
    public SimpleApplicationEventMulticaster simpleApplicationEventMulticaster(){
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return simpleApplicationEventMulticaster;
    }

    @Bean
    public ThreadPoolTaskExecutor eventExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorsConfig.getCorePoolSize());
        executor.setMaxPoolSize(executorsConfig.getMaxPoolSize());
        executor.setKeepAliveSeconds(executorsConfig.getKeepAliveSeconds());
        executor.setQueueCapacity(executorsConfig.getQueueCapacity());
        executor.setThreadNamePrefix(executorsConfig.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
